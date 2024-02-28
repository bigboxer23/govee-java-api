package com.bigboxer23.govee;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.bigboxer23.govee.data.GoveeEvent;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.io.IOException;

/** */
public abstract class GoveeEventSubscriber implements IGoveeEventSubscriber {
	@Override
	public void successfulConnection(Mqtt3ConnAck connAck) {}

	@Override
	public void successfulSubscription() {}

	@Override
	public void exception(Throwable throwable) {}

	@Override
	public void messageReceived(Mqtt3Publish publish) throws IOException {
		messageReceived(
				GoveeApi.moshi.adapter(GoveeEvent.class).fromJson(new String(publish.getPayloadAsBytes(), UTF_8)));
	}
}
