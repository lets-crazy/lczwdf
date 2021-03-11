package com.lcz.wdf.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * TODO
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/9 13:59
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer wdf_consumer_group = new DefaultMQPushConsumer("wdf_consumer_group");

        wdf_consumer_group.setNamesrvAddr("127.0.0.1:9876");

        wdf_consumer_group.setConsumeMessageBatchMaxSize(2);

        wdf_consumer_group.subscribe("Topic_wdf", "Tags");

        wdf_consumer_group.setMessageListener(

                new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(
                            List<MessageExt> msgs,
                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                        for (MessageExt msg : msgs) {
                            try {
                                //
                                String topic = msg.getTopic();

                                String tags = msg.getTags();

                                byte[] body = msg.getBody();
                                String result = new String(body, RemotingHelper.DEFAULT_CHARSET);

                                System.out.println();

                            } catch
                            (UnsupportedEncodingException e) {
                                e.printStackTrace();

                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                        }

                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                });

        wdf_consumer_group.start();
    }
}
