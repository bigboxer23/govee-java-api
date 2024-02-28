package com.bigboxer23.govee;

import com.bigboxer23.govee.data.GoveeDeviceCapability;
import com.bigboxer23.govee.data.GoveeDeviceRange;
import com.bigboxer23.govee.data.GoveeDeviceStatusRequest;
import java.util.HashMap;
import java.util.Map;

/** */
public class IHumidifierCommands {
	private static final String ON_OFF_TYPE = "devices.capabilities.on_off";
	private static final String ON_OFF_INSTANCE = "powerSwitch";

	private static final String WORK_MODE_TYPE = "devices.capabilities.work_mode";

	private static final String WORK_MODE_INSTANCE = "workMode";

	private static final String RANGE_TYPE = "devices.capabilities.range";

	private static final String RANGE_INSTANCE = "humidity";

	private static final String WORK_MODE = "workMode";

	private static final String MODE_VALUE = "modeValue";

	public static GoveeDeviceStatusRequest turnOn(String model, String deviceId) {
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(ON_OFF_TYPE, ON_OFF_INSTANCE, 1));
	}

	public static GoveeDeviceStatusRequest turnOff(String model, String deviceId) {
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(ON_OFF_TYPE, ON_OFF_INSTANCE, 0));
	}

	public static GoveeDeviceStatusRequest setManualHumidityMode(String model, String deviceId, int targetLevel) {
		Map<String, Integer> capabilities = new HashMap<>();
		capabilities.put(WORK_MODE, 1);
		capabilities.put(MODE_VALUE, targetLevel);
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(WORK_MODE_TYPE, WORK_MODE_INSTANCE, capabilities));
	}

	public static GoveeDeviceStatusRequest setAutoHumidityMode(String model, String deviceId) {
		Map<String, Object> capabilities = new HashMap<>();
		capabilities.put(WORK_MODE, 3);
		capabilities.put(MODE_VALUE, new GoveeDeviceRange(-1, -1));
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(WORK_MODE_TYPE, WORK_MODE_INSTANCE, capabilities));
	}

	public static GoveeDeviceStatusRequest setAutoHumidityTargetPercent(
			String model, String deviceId, int humidityPercent) {
		return new GoveeDeviceStatusRequest(
				model, deviceId, new GoveeDeviceCapability(RANGE_TYPE, RANGE_INSTANCE, humidityPercent));
	}
}
