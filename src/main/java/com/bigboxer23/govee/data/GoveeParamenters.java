package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeParamenters {
	private String dataType;

	private String unit;

	private List<GoveeDeviceOptions> options;

	private List<GoveeDeviceField> fields;

	private GoveeDeviceRange range;
}
