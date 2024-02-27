package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeGetDevicesResponse extends GoveeAPIResponse {
	private List<GoveeDevice> data;
}
