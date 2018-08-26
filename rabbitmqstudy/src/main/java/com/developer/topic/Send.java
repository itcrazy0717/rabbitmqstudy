package com.developer.topic;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/26 14:26
 * @description:topic模式的消息发送
 */

public class Send {

    private final static String EXCHANGE_NAME="test_exchange_topic";
    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg="topic msg.....";

        channel.basicPublish(EXCHANGE_NAME,"goods.add",null,msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,"delete goods".getBytes());

        channel.close();

        connection.close();


    }
}
