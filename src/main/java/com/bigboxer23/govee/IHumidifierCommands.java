package com.bigboxer23.govee;

import com.bigboxer23.govee.data.GoveeDeviceCapability;
import com.bigboxer23.govee.data.GoveeDeviceStatusRequest;

/** */
public class IHumidifierCommands {
	private static final String ON_OFF_TYPE = "devices.capabilities.on_off";
	private static final String ON_OFF_INSTANCE = "powerSwitch";

	public static GoveeDeviceStatusRequest turnOn(String model, String deviceId) {
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(ON_OFF_TYPE, ON_OFF_INSTANCE, 1));
	}

	public static GoveeDeviceStatusRequest turnOff(String model, String deviceId) {
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(ON_OFF_TYPE, ON_OFF_INSTANCE, 0));
	}
}
