package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceStatusRequest {
	private String requestId;

	private GoveeDeviceStatusRequestPayload payload;

	public GoveeDeviceStatusRequest() {}

	public GoveeDeviceStatusRequest(String sku, String deviceId) {
		setRequestId("uuid");
		setPayload(new GoveeDeviceStatusRequestPayload(sku, deviceId));
	}
}
