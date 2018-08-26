package com.developer.confirm;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/26 15:28
 * @description: confirm简单模式
 */

public class Send {

    private final static String QUEUE_NAME = "queue_confirm";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //开启confirm模式
        channel.confirmSelect();


        String msg = "confirm simple msg";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        if (channel.waitForConfirms()) {
            System.out.println("send success......");
        } else {
            System.out.println("send failed");
        }

        channel.close();
        connection.close();

    }
}
