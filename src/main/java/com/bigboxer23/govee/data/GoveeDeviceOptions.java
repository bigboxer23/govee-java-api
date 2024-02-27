package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeDeviceOptions {
	private String name;

	private int value;

	private List<GoveeOptionValue> options;

	private boolean required;

	private int defaultValue;

	private GoveeDeviceRange range;
}
