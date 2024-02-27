package com.bigboxer23.govee;

import static org.junit.jupiter.api.Assertions.*;

import com.bigboxer23.govee.data.GoveeDevice;
import com.bigboxer23.utils.properties.PropertyUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/** */
public class GoveeApiTest {
	private static final String API_KEY = PropertyUtils.getProperty("govee_api_key");

	private static final String TEST_SKU = PropertyUtils.getProperty("govee_device_sku");

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
				.getDeviceStatus(TEST_SKU, TEST_DEVICEID)
				.getDevice();
		assertNotNull(device);
		assertEquals(TEST_SKU, device.getSku());
		assertEquals(TEST_DEVICEID, device.getDevice());
		assertFalse(device.getCapabilities().isEmpty());
	}
}
