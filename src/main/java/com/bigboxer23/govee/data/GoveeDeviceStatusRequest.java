package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceStatusRequest {
	private String requestId;

	private GoveeDeviceStatusRequestPayload payload;

	public GoveeDeviceStatusRequest() {}

	public GoveeDeviceStatusRequest(String sku, String deviceId, GoveeDeviceCapability capability) {
		setRequestId("uuid");
		setPayload(new GoveeDeviceStatusRequestPayload(sku, deviceId, capability));
	}

	public GoveeDeviceStatusRequest(String sku, String deviceId) {
		this(sku, deviceId, null);
	}
}
