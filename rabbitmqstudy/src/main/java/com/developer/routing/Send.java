package com.developer.routing;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/25 22:52
 * @description:路由发送消息
 */

public class Send {

    private final static String EXCHANGE_NAME = "EXCHANGE_NAME_ROUTING";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String msg = "hello world";

        //路由模式，根据路由key来找到具体的消费者
        //在消费者端进行绑定的时候，根据具体key进行绑定
        String routingKey = "test_routing_key";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "info", null, "test".getBytes());


        channel.close();

        connection.close();

    }
}
