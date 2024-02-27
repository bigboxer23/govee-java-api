package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeDevice {
	private String sku;

	private String device;

	private String deviceName;

	private String type;

	private List<GoveeDeviceCapability> capabilities;
}
