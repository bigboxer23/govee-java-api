package com.bigboxer23.govee;

import static org.junit.jupiter.api.Assertions.*;

import com.bigboxer23.govee.data.GoveeDevice;
import com.bigboxer23.govee.data.GoveeDeviceCommandResponse;
import com.bigboxer23.govee.data.GoveeDeviceStatusRequest;
import com.bigboxer23.utils.properties.PropertyUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/** */
public class GoveeApiTest {
	private static final String API_KEY = PropertyUtils.getProperty("govee_api_key");

	private static final String TEST_MODEL = PropertyUtils.getProperty("govee_device_model");

	private static final String TEST_DEVICEID = PropertyUtils.getProperty("govee_device_id");

	@Test
	public void getDevices() throws IOException {
		List<GoveeDevice> devices = GoveeApi.getInstance(API_KEY).getDevices().getData();
		assertNotNull(devices);
		assertFalse(devices.isEmpty());
	}

	@Test
	public void getDeviceStatus() throws IOException {
		GoveeDevice device = GoveeApi.getInstance(API_KEY)
				.getDeviceStatus(TEST_MODEL, TEST_DEVICEID)
				.getDevice();
		assertNotNull(device);
		assertEquals(TEST_MODEL, device.getSku());
		assertEquals(TEST_DEVICEID, device.getDevice());
		assertFalse(device.getCapabilities().isEmpty());
	}

	@Test
	public void humidifierOnOff() throws IOException {
		// On
		GoveeDeviceCommandResponse response =
				GoveeApi.getInstance(API_KEY).sendDeviceCommand(IHumidifierCommands.turnOn(TEST_MODEL, TEST_DEVICEID));
		assertNotNull(response);
		assertEquals("success", response.getCapability().getState().getStatus());

		// Off
		response =
				GoveeApi.getInstance(API_KEY).sendDeviceCommand(IHumidifierCommands.turnOff(TEST_MODEL, TEST_DEVICEID));
		assertNotNull(response);
		assertEquals("success", response.getCapability().getState().getStatus());

		// invalid deviceId
		try {
			response =
					GoveeApi.getInstance(API_KEY).sendDeviceCommand(IHumidifierCommands.turnOff(TEST_MODEL, "invalid"));
			fail();
		} catch (IOException e) {
		}

		// invalid model
		try {
			response = GoveeApi.getInstance(API_KEY)
					.sendDeviceCommand(IHumidifierCommands.turnOff("invalid", TEST_DEVICEID));
			fail();
		} catch (IOException e) {
		}

		// invalid state
		try {
			GoveeDeviceStatusRequest turnOffCommand = IHumidifierCommands.turnOff(TEST_MODEL, TEST_DEVICEID);
			turnOffCommand.getPayload().getCapability().setValue(2);
			response = GoveeApi.getInstance(API_KEY).sendDeviceCommand(turnOffCommand);
			fail();
		} catch (IOException e) {
		}
	}
}
