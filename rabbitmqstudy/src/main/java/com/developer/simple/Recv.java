package com.developer.simple;

import java.io.IOException;
import java.util.Date;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author: developer
 * @date: 2018/8/25 14:43
 * @description: 接收mq消息
 */

public class Recv {

    private final static String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws Exception {

        //获取mq连接
        Connection connection = ConnectionUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //声明队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {

            //重写处理交互的分发
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "UTF-8");

                System.out.println("recv msg:" + msg + "  接收时间：" + ConnectionUtils.getNowDate());
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
