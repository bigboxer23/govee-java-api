package com.bigboxer23.govee.data;

import com.squareup.moshi.Json;
import lombok.Data;

/**
 *
 */
@Data
public class GoveeDeviceStatusResponse extends GoveeAPIResponse
{
	@Json(name = "payload")
	private GoveeDevice device;
}
