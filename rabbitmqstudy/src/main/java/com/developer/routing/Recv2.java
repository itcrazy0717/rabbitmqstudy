package com.developer.routing;

import java.io.IOException;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author: developer
 * @date: 2018/8/25 22:59
 * @description:
 */

public class Recv2 {

    private final static String QUEUE_NAME = "test_queue_routing1";
    private final static String EXCHANGE_NAME = "EXCHANGE_NAME_ROUTING";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "test_routing_key");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String recMsg = new String(body, "utf-8");

                System.out.println("2 recv:" + recMsg);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    channel.basicAck(envelope.getDeliveryTag(), false);

                }
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
