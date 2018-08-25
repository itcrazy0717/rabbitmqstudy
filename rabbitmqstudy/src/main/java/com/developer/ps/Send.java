package com.developer.ps;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/25 21:46
 * @description: 订阅模式进行消息的发送
 */

public class Send {

    private final static String EXCHANGE_NAME = "TEST_EXCHANGE_NAME";

    public static void main(String[] args) throws Exception {

        //获取mq连接
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String msg="hello world";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        channel.close();

        connection.close();
    }
}
