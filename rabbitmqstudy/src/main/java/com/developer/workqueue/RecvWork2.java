package com.developer.workqueue;

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
 * @date: 2018/8/25 15:37
 * @description:workqueue队列演示 --接收消息
 */

public class RecvWork2 {

    private final static String QUEUE_NAME = "WORK_QUEUE";

    private static int count = 0;

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "utf-8");

                System.out.println("2 recv:" + msg + " 接收时间：" + ConnectionUtils.getNowDate());

                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    System.out.println("2 done");
                }
                System.out.println("总数:" + count);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);


    }
}
