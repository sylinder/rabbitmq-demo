package com.yuanzhu.rabbitmqdemo.senders;

import com.yuanzhu.rabbitmqdemo.RabbitmqDemoApplication;
import com.yuanzhu.rabbitmqdemo.recivers.HelloReceiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final HelloReceiver helloReceiver;

    public Runner(RabbitTemplate rabbitTemplate, HelloReceiver helloReceiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.helloReceiver = helloReceiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.topicExchangeName,
                "foo.bar.baz", "Hello from RabbitMQ");
        helloReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
