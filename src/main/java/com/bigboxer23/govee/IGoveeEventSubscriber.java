package com.bigboxer23.govee;

import com.bigboxer23.govee.data.GoveeEvent;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.io.IOException;

/** */
public interface IGoveeEventSubscriber {
	void successfulConnection(Mqtt3ConnAck connAck);

	void successfulSubscription();

	void exception(Throwable throwable);

	void messageReceived(Mqtt3Publish publish) throws IOException;

	void messageReceived(GoveeEvent event);
}
