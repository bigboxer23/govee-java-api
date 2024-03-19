package com.bigboxer23.govee.data;

import com.squareup.moshi.Json;
import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeDevice {
	@Json(name = "sku")
	private String model;

	@Json(name = "device")
	private String deviceId;

	private String deviceName;

	private String type;

	private List<GoveeDeviceCapability> capabilities;
}
