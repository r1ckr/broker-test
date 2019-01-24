package com.r1ckr;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class Publisher implements Runnable {

    private int numMessages;
    private String topic;

    public Publisher(int numMessages, String topic) {
        this.numMessages = numMessages;
        this.topic = topic;
    }

    @Override
    public void run() {
        System.out.println("Running publisher... ");
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();

        RedisPubSubCommands<String, String> sync = connection.sync();
        System.out.println("Sending "+numMessages+" messages...");

        for (int i = 0; i < numMessages; i++) {
            sync.publish(topic, "element"+i);
        }
        connection.close();
    }
}
