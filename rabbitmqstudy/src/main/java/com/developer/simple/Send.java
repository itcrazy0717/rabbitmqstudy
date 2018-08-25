package com.developer.simple;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/25 14:29
 * @description: 发送mq数据 注意步骤
 */

public class Send {

    private final static String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws Exception {

        //获取mq的连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello world";

        //发送消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("send msg:" + msg);

        channel.close();

        connection.close();
    }
}
