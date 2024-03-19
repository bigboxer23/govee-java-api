package com.bigboxer23.govee;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.bigboxer23.govee.data.*;
import com.bigboxer23.utils.http.OkHttpUtil;
import com.bigboxer23.utils.http.RequestBuilderCallback;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** */
public class GoveeApi {
	private static final Logger logger = LoggerFactory.getLogger(GoveeApi.class);
	private static final String API_URL = "https://openapi.api.govee.com/router/api/v1/";

	private static final String MQTT_URL = "mqtt.openapi.govee.com";

	private static final int MQTT_PORT = 8883;

	private static GoveeApi instance;

	private final String apiKey;

	public static final Moshi moshi = new Moshi.Builder().build();

	private GoveeApi(String apiKey) {
		this.apiKey = apiKey;
	}

	public static GoveeApi getInstance(String apiKey) {
		if (apiKey == null) {
			logger.error("need to define apiKey.");
			throw new RuntimeException("need to define apiKey.");
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
		try (Response response = OkHttpUtil.getSynchronous(API_URL + "user/devices", addAuth())) {
			return parseResponse(response, GoveeGetDevicesResponse.class);
		}
	}

	public GoveeDeviceStatusResponse getDeviceStatus(String model, String deviceId) throws IOException {
		try (Response response = OkHttpUtil.postSynchronous(
				API_URL + "device/state",
				RequestBody.create(URLDecoder.decode(
								getMoshi()
										.adapter(GoveeDeviceStatusRequest.class)
										.toJson(new GoveeDeviceStatusRequest(model, deviceId)),
								StandardCharsets.UTF_8.displayName())
						.getBytes(StandardCharsets.UTF_8)),
				addAuth())) {
			return parseResponse(response, GoveeDeviceStatusResponse.class);
		}
	}

	public GoveeDeviceCommandResponse sendDeviceCommand(GoveeDeviceStatusRequest command) throws IOException {
		try (Response response = OkHttpUtil.postSynchronous(
				API_URL + "device/control",
				RequestBody.create(URLDecoder.decode(
								getMoshi()
										.adapter(GoveeDeviceStatusRequest.class)
										.toJson(command),
								StandardCharsets.UTF_8.displayName())
						.getBytes(StandardCharsets.UTF_8)),
				addAuth())) {
			return parseResponse(response, GoveeDeviceCommandResponse.class);
		}
	}

	public void subscribeToGoveeEvents(IGoveeEventSubscriber subscriber) {
		Mqtt3AsyncClient client = Mqtt3Client.builder()
				.identifier(apiKey)
				.serverHost(MQTT_URL)
				.serverPort(MQTT_PORT)
				.sslConfig()
				.applySslConfig()
				.simpleAuth(Mqtt3SimpleAuth.builder()
						.username(apiKey)
						.password(apiKey.getBytes(UTF_8))
						.build())
				.buildAsync();

		client.publishes(MqttGlobalPublishFilter.ALL, publish -> {
			logger.info("subscribeToGoveeEvents:message received");
			try {
				subscriber.messageReceived(publish);
			} catch (IOException e) {
				logger.warn("subscribeToGoveeEvents", e);
			}
		});

		client.connect()
				.thenCompose(connAck -> {
					logger.info("subscribeToGoveeEvents:successful connection");
					subscriber.successfulConnection(connAck);
					return client.subscribeWith().topicFilter("GA/" + apiKey).send();
				})
				.thenRun(() -> {
					logger.info("subscribeToGoveeEvents:successful subscription");
					subscriber.successfulSubscription();
				})
				.exceptionally(throwable -> {
					logger.error("subscribeToGoveeEvents", throwable);
					subscriber.exception(throwable);
					return null;
				});
	}
}
