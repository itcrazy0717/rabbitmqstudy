package com.developer.confirm;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/26 15:28
 * @description: confirm 批量确认
 */

public class Send2 {

    private final static String QUEUE_NAME = "queue_confirm1";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //开启confirm模式
        channel.confirmSelect();
        int prefetchSize = 1;
        channel.basicQos(prefetchSize);

        String msg = "confirm simple msg";

        for (int i = 0; i < 10; i++) {

            channel.basicPublish("", QUEUE_NAME, null, (msg + i).getBytes());
        }

        //批量确认也就是 发送完了在确认
        if (channel.waitForConfirms()) {
            System.out.println("send success......");
        } else {
            System.out.println("send failed");
        }

        channel.close();
        connection.close();

    }
}
