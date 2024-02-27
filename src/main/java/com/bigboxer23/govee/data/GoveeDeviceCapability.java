package com.bigboxer23.govee.data;

import lombok.Data;

/** */
@Data
public class GoveeDeviceCapability {
	private String type;

	private String instance;

	private int alarmType;

	private GoveeEventState eventState;

	private GoveeParamenters parameters;

	private GoveeOptionValue state;
}
