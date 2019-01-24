package com.r1ckr;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber implements Runnable {
    private int messagesReceived=0;
    private int numMessagesShouldReceive;
    private String topic;

    public JedisSubscriber(int numMessagesShouldReceive, String topic) {
        this.numMessagesShouldReceive = numMessagesShouldReceive;
        this.topic = topic;
    }

    public int getMessagesReceived() {
        return messagesReceived;
    }

    @Override
    public void run() {
        System.out.println("Running subscriber...");
        Jedis jedis = new Jedis("localhost");
        jedis.subscribe(listener, topic);
        System.out.println("Subscribed");
    }

    JedisPubSub listener = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println(channel+":"+message);
            messagesReceived++;
        }
    };
}
