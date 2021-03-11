package com.lcz.wdf.mq.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * TODO
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/9 13:48
 */
public class Producer {

    public static void main(String[] args)
            throws MQClientException, UnsupportedEncodingException, RemotingException,
                    InterruptedException, MQBrokerException {
        DefaultMQProducer wdf_producer_group = new DefaultMQProducer("wdf_producer_group");

        wdf_producer_group.setNamesrvAddr("127.0.0.1:9876");

        wdf_producer_group.start();

        Message message =
                new Message(
                        "Topic_wdf",
                        "Tags",
                        "Keys_1",
                        "hello".getBytes(RemotingHelper.DEFAULT_CHARSET));

        for (int i = 0; i < 5; i++) {

            SendResult send =
                    wdf_producer_group.send(
                            message,
                            new MessageQueueSelector() {
                                @Override
                                public MessageQueue select(
                                        List<MessageQueue> mqs, Message message, Object o) {

                                    return mqs.get((Integer) o);
                                }
                            },
                            1);
        }

        wdf_producer_group.shutdown();
    }
}
