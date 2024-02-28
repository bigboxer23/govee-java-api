package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceStatusRequestPayload {
	private String sku;

	private String device;

	private GoveeDeviceCapability capability;

	public GoveeDeviceStatusRequestPayload() {}

	public GoveeDeviceStatusRequestPayload(String sku, String deviceId, GoveeDeviceCapability capability) {
		setSku(sku);
		setDevice(deviceId);
		setCapability(capability);
	}
}
