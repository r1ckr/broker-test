package com.r1ckr;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Lettuce tests
     */
    @Test
    public void lettuceTests() throws InterruptedException {
        int messagesToSend = 1000;
        Publisher publisher = new Publisher(messagesToSend, "topic");
//        Publisher publisher2 = new Publisher(messagesToSend, "topic");
        Subscriber subscriber1 = new Subscriber(messagesToSend, "topic");
        Subscriber subscriber2 = new Subscriber(messagesToSend, "topic");
        Subscriber subscriber3 = new Subscriber(messagesToSend, "topic");
        Subscriber subscriber4 = new Subscriber(messagesToSend, "topic");
        Subscriber subscriber5 = new Subscriber(messagesToSend, "topic");
//
        subscriber1.run();
        subscriber2.run();
        subscriber3.run();
        subscriber4.run();
        subscriber5.run();

        Thread.sleep(500);
        publisher.run();
//        publisher2.run();

        Thread.sleep(5000);


        assertThat(subscriber1.getMessagesReceived()).isEqualTo(messagesToSend);
        assertThat(subscriber3.getMessagesReceived()).isEqualTo(messagesToSend);
        assertThat(subscriber4.getMessagesReceived()).isEqualTo(messagesToSend);
        assertThat(subscriber5.getMessagesReceived()).isEqualTo(messagesToSend);
        assertThat(subscriber2.getMessagesReceived()).isEqualTo(messagesToSend);
    }

    /**
     * Jedis tests
     */
    @Test
    public void jedisTests() throws InterruptedException {
        System.out.println("Running this");
        int messagesToSend = 10000;
        JedisPublisher publisher = new JedisPublisher(messagesToSend, "topic");
        JedisPublisher publisher2 = new JedisPublisher(messagesToSend, "topic");
        JedisSubscriber subscriber1 = new JedisSubscriber(messagesToSend, "topic");
        JedisSubscriber subscriber2 = new JedisSubscriber(messagesToSend, "topic");
        JedisSubscriber subscriber3 = new JedisSubscriber(messagesToSend, "topic");
        JedisSubscriber subscriber4 = new JedisSubscriber(messagesToSend, "topic");
        JedisSubscriber subscriber5 = new JedisSubscriber(messagesToSend, "topic");
//
//        subscriber1.run();

        new Thread(subscriber1).start();
        new Thread(subscriber2).start();
        new Thread(subscriber3).start();
        new Thread(subscriber4).start();
        new Thread(subscriber5).start();

        Thread.sleep(500);
        new Thread(publisher).start();
        new Thread(publisher2).start();

        Thread.sleep(10000);

        assertThat(subscriber1.getMessagesReceived()).isEqualTo(messagesToSend*2);
        assertThat(subscriber3.getMessagesReceived()).isEqualTo(messagesToSend*2);
        assertThat(subscriber4.getMessagesReceived()).isEqualTo(messagesToSend*2);
        assertThat(subscriber5.getMessagesReceived()).isEqualTo(messagesToSend*2);
        assertThat(subscriber2.getMessagesReceived()).isEqualTo(messagesToSend*2);
    }
}
