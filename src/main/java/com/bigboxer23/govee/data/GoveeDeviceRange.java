package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceRange {
	private int min;

	private int max;

	private int precision;

	public GoveeDeviceRange(int min, int max) {
		setMin(min);
		setMax(max);
	}
}
