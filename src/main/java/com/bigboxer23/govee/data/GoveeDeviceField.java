package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeDeviceField {
	private String fieldName;

	private String dataType;

	private List<GoveeDeviceOptions> options;

	private boolean required;
}
