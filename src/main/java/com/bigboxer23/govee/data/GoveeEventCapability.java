package com.bigboxer23.govee.data;

import java.util.List;
import lombok.Data;

/** */
@Data
public class GoveeEventCapability {
	private String type;

	private String instance;

	private List<GoveeEventStateOption> state;
}
