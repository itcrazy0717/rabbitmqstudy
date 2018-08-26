package com.developer.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.developer.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

/**
 * @author: developer
 * @date: 2018/8/26 15:28
 * @description: confirm异步模式
 */

public class Send3 {

    private final static String QUEUE_NAME = "queue_confirm3";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //开启confirm模式
        channel.confirmSelect();

        SortedSet<Long> confirmset = Collections.synchronizedSortedSet(new TreeSet());

        channel.addConfirmListener(new ConfirmListener() {

            //消息成功
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {

                if (multiple) {

                    System.out.println("handleAck......multiple...true");
                    confirmset.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleAck......multiple...false");
                    confirmset.remove(deliveryTag);
                }

            }

            //消息发送失败进行处理
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {

                if (multiple) {

                    System.out.println("handleNack......multiple...true");
                    confirmset.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleNack......multiple...false");
                    confirmset.remove(deliveryTag);
                }

            }
        });


        String msg = "confirm simple msg";

        for (int i = 0; i < 10; i++) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmset.add(seqNo);
        }

    }
}
