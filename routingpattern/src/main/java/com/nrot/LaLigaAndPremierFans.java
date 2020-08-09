package com.nrot;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LaLigaAndPremierFans {
    private static final String EXCHANGE_NAME = "football_direct";
    private static final String HOST = "localhost";
    private static final String HOBBY = "Premier League";//routing key
    private static final String SECONDHOBBY = "LA LIGA";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, HOBBY);
        channel.queueBind(queueName, EXCHANGE_NAME, SECONDHOBBY);

        System.out.println(SECONDHOBBY + " fan is waiting for message");
        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" Received '" + delivery.getEnvelope().getRoutingKey() + "': '" + message + "'");
        });
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
