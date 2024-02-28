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

	private Object value;

	public GoveeDeviceCapability(String type, String instance, Object value) {
		setType(type);
		setInstance(instance);
		setValue(value);
	}
}
