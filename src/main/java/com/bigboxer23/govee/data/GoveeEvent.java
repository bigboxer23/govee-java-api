package com.bigboxer23.govee.data;

import com.squareup.moshi.Json;
import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeEvent {
	@Json(name = "sku")
	private String model;

	@Json(name = "device")
	private String deviceId;

	private String deviceName;

	private List<GoveeEventCapability> capabilities;

	public boolean isLackWaterEvent() {
		if (getCapabilities() == null || getCapabilities().isEmpty()) {
			return false;
		}
		return getCapabilities().get(0).getInstance() != null
				&& "lackWaterEvent".equalsIgnoreCase(getCapabilities().get(0).getInstance());
	}
}
