package com.developer.workfair;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/25 15:19
 * @description:workqueue队列演示
 */

public class SendWorkFair {

    private final static String QUEUE_NAME = "WORK_QUEUE";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //每个消费者发送确认消息之前，消息队列不发送下一个消息给消费者，一次只处理一条消息
        //限制发送给同一个消费者的消息为1条
        int prefetchSize = 1;
        channel.basicQos(prefetchSize);

        for (int i = 0; i < 50; i++) {

            String msg = "send hello" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            System.out.println("send msg:" + msg);
        }

        channel.close();

        connection.close();

    }
}
