package com.bigboxer23.govee;

import com.bigboxer23.govee.data.GoveeAPIResponse;
import com.bigboxer23.govee.data.GoveeGetDevicesResponse;
import com.bigboxer23.utils.http.OkHttpUtil;
import com.bigboxer23.utils.http.RequestBuilderCallback;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.util.Optional;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** */
public class GoveeApi {
	private static final Logger logger = LoggerFactory.getLogger(GoveeApi.class);
	protected static final String baseUrl = "https://openapi.api.govee.com/";

	private static GoveeApi instance;

	private final String apiKey;

	private final Moshi moshi = new Moshi.Builder().build();

	private GoveeApi(String apiKey) {
		this.apiKey = apiKey;
	}

	public static GoveeApi getInstance(String apiKey) throws IOException {
		if (apiKey == null) {
			logger.error("need to define apiKey.");
			throw new IOException("need to define apiKey.");
		}
		return Optional.ofNullable(instance).orElseGet(() -> {
			instance = new GoveeApi(apiKey);
			return instance;
		});
	}

	protected Moshi getMoshi() {
		return moshi;
	}

	protected RequestBuilderCallback addAuth() {
		return builder -> builder.addHeader("Govee-API-Key", apiKey).addHeader("Content-Type", "application/json");
	}

	private <T extends GoveeAPIResponse> T parseResponse(Response response, Class<T> clazz) throws IOException {
		if (!response.isSuccessful()) {
			throw new IOException(response.code() + " " + response.message() + " "
					+ response.body().string());
		}
		String body = response.body().string();
		T apiResponse = getMoshi().adapter(clazz).fromJson(body);
		if (!checkForError(apiResponse)) {
			throw new IOException(response.code() + " " + response.message() + " " + body);
		}
		return apiResponse;
	}

	/**
	 * add standardized logging and error checking on request
	 *
	 * @param apiResponse the API response
	 * @return true if error occurs
	 */
	private boolean checkForError(GoveeAPIResponse apiResponse) {
		return Optional.ofNullable(apiResponse)
				.map(response -> {
					if (response.getCode() != 200) {
						logger.error("error code: " + response.getCode() + " : " + response.getMessage());
						return false;
					}
					return true;
				})
				.orElseGet(() -> {
					logger.error("null api response");
					return false;
				});
	}

	public GoveeGetDevicesResponse getDevices() throws IOException {
		try (Response response = OkHttpUtil.getSynchronous(baseUrl + "router/api/v1/user/devices", addAuth())) {
			return parseResponse(response, GoveeGetDevicesResponse.class);
		}
	}
}
