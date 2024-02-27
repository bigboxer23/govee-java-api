package com.bigboxer23.govee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bigboxer23.govee.data.GoveeDevice;
import com.bigboxer23.utils.properties.PropertyUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

/** */
public class GoveeApiTest {
	private static final String API_KEY = PropertyUtils.getProperty("govee_api_key");

	@Test
	public void getDevices() throws IOException {
		List<GoveeDevice> devices = GoveeApi.getInstance(API_KEY).getDevices().getData();
		assertNotNull(devices);
		assertFalse(devices.isEmpty());
	}
}
