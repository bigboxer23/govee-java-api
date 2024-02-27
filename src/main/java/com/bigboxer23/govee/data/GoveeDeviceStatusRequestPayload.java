package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceStatusRequestPayload {
	private String sku;

	private String device;

	public GoveeDeviceStatusRequestPayload() {}

	public GoveeDeviceStatusRequestPayload(String sku, String deviceId) {
		setSku(sku);
		setDevice(deviceId);
	}
}
