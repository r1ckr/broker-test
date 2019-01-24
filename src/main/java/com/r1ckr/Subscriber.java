package com.r1ckr;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class Subscriber implements Runnable {
    private int messagesReceived=0;
    private int numMessagesShouldReceive;
    private String topic;

    public Subscriber(int numMessagesShouldReceive, String topic) {
        this.numMessagesShouldReceive = numMessagesShouldReceive;
        this.topic = topic;
    }

    public int getMessagesReceived() {
        return messagesReceived;
    }

    @Override
    public void run() {
        System.out.println("Running subscriber... ");
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
        connection.addListener(listener);

        RedisPubSubCommands<String, String> sync = connection.sync();
        sync.subscribe(topic);
        System.out.println("Subscribed");

    }

    RedisPubSubListener<String, String> listener = new RedisPubSubListener<String, String>() {
        @Override
        public void message(String s, String s2) {
            System.out.println(s+":"+s2);
            messagesReceived++;
            try {
                Thread.sleep(5);
            }catch (Exception e){
                System.out.println("Ignoring exception");
            }

        }

        @Override
        public void message(String s, String k1, String s2) {
        }

        @Override
        public void subscribed(String s, long l) {

        }

        @Override
        public void psubscribed(String s, long l) {

        }

        @Override
        public void unsubscribed(String s, long l) {

        }

        @Override
        public void punsubscribed(String s, long l) {

        }
    };
}
