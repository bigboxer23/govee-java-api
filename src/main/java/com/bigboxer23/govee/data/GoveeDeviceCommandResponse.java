package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceCommandResponse extends GoveeAPIResponse {
	private GoveeDeviceCapability capability;
}
