package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeEvent {
	private String sku;

	private String device;

	private String deviceName;

	private List<GoveeEventCapability> capabilities;
}
