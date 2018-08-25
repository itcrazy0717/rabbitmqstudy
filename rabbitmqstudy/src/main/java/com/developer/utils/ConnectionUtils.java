package com.developer.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author: developer
 * @date: 2018/8/24 0:25
 * @description:
 */

public class ConnectionUtils {

    /**
     * 获取mq的连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("127.0.0.1");

        connectionFactory.setPort(5672);

        connectionFactory.setVirtualHost("/vhost_mmr");

        connectionFactory.setUsername("admin");

        connectionFactory.setPassword("cdx@2018");

        return connectionFactory.newConnection();

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowDate() {

        LocalDateTime localDateTime = LocalDateTime.now();

        String format = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return format;
    }

}
