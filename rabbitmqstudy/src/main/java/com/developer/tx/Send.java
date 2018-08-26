package com.developer.tx;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/26 15:03
 * @description: 利用事务进行消息发送
 */

public class Send {

    private final static String QUEUE_NAME = "tx_queue_name";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //开启AMQP的事务
        channel.txSelect();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        try {

            int  x=10/0;
            channel.basicPublish("", QUEUE_NAME, null, "tx msg".getBytes());
            channel.txCommit();

        } catch (Exception e) {

            channel.txRollback();

            System.out.println("tx rollback");
        }

        channel.close();

        connection.close();


    }
}
