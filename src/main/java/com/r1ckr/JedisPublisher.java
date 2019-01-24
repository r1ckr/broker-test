package com.r1ckr;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import redis.clients.jedis.Jedis;

public class JedisPublisher implements Runnable {

    private int numMessages;
    private String topic;

    public JedisPublisher(int numMessages, String topic) {
        this.numMessages = numMessages;
        this.topic = topic;
    }

    @Override
    public void run() {
        System.out.println("Running publisher... ");
        Jedis jedis = new Jedis("localhost");

        System.out.println("Sending "+numMessages+" messages...");

        for (int i = 0; i < numMessages; i++) {
            jedis.publish(topic, "element"+i);
        }
        jedis.close();
    }
}
