package com.bigboxer23.govee;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bigboxer23.govee.data.*;
import com.bigboxer23.utils.http.OkHttpUtil;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GoveeApiUnitTest {
	private GoveeApi goveeApi;

	@Mock
	private OkHttpUtil mockHttpUtil;

	private static final String TEST_API_KEY = "mock-api-key";
	private static final String TEST_MODEL = "H7142";
	private static final String TEST_DEVICEID = "18:12:24:A4:F8:40:79:6C";

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetDevices_Success() throws IOException {
		try (MockWebServer server = new MockWebServer()) {
			GoveeApi.setApiUrl(server.url("/").toString());
			goveeApi = GoveeApi.getInstance(TEST_API_KEY);

			server.enqueue(new MockResponse()
					.setResponseCode(200)
					.setBody("{\"code\":200,\"data\":[{\"device\":\"18:12:24:A4:F8:40:79:6C\",\"sku\":\"H7142\"}]}"));
			List<GoveeDevice> devices = goveeApi.getDevices().getData();
			assertNotNull(devices);
			assertFalse(devices.isEmpty());
			assertEquals(TEST_MODEL, devices.get(0).getModel());
			assertEquals(TEST_DEVICEID, devices.get(0).getDeviceId());
		}
	}

	@Test
	public void testGetDeviceStatus_Error() throws IOException {
		try (MockWebServer server = new MockWebServer()) {
			GoveeApi.setApiUrl(server.url("/").toString());
			goveeApi = GoveeApi.getInstance(TEST_API_KEY);

			server.enqueue(new MockResponse().setResponseCode(404));

			assertThrows(IOException.class, () -> goveeApi.getDeviceStatus(TEST_MODEL, TEST_DEVICEID));
		}
	}

	@Test
	public void testSendDeviceCommand_Success() throws IOException {
		try (MockWebServer server = new MockWebServer()) {
			GoveeApi.setApiUrl(server.url("/").toString());
			goveeApi = GoveeApi.getInstance(TEST_API_KEY);

			server.enqueue(new MockResponse()
					.setResponseCode(200)
					.setBody("{\"code\":200,\"capability\":{\"state\":{\"status\":\"success\"}}}"));

			GoveeDeviceCommandResponse response =
					goveeApi.sendDeviceCommand(IHumidifierCommands.turnOn(TEST_MODEL, TEST_DEVICEID));
			assertNotNull(response);
			assertEquals("success", response.getCapability().getState().getStatus());
		}
	}

	@Test
	public void testSubscribeToGoveeEvents_Mock() throws IOException, InterruptedException {
		GoveeEventSubscriber mockSubscriber = mock(GoveeEventSubscriber.class);

		// No need for MockWebServer here, since this is event-based
		goveeApi = GoveeApi.getInstance(TEST_API_KEY);
		goveeApi.subscribeToGoveeEvents(mockSubscriber);

		// Simulate receiving a message
		GoveeEvent event = new GoveeEvent();
		event.setDeviceId(TEST_DEVICEID);
		event.setModel(TEST_MODEL);

		mockSubscriber.messageReceived(event);
		verify(mockSubscriber).messageReceived(any(GoveeEvent.class));
	}
}
