package com.yuanzhu.rabbitmqdemo.recivers;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class HelloReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("===========================");
        System.out.println("Received < " + message + " >");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
