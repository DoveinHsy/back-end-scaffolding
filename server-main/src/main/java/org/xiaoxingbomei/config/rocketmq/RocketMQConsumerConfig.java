package org.xiaoxingbomei.config.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RocketMQConsumerConfig {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.consumer.batchSize}")
    private int batchSize;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer()
    {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);  // 从最早的偏移量开始消费

        // 消费者的订阅配置
        try {
            consumer.subscribe(topic, "*"); // 订阅指定的topic
        } catch (Exception e) {
            e.printStackTrace();
        }
        return consumer;
    }

    // 配置消息消费回调
    @Bean
    public MessageListenerConcurrently messageListenerConcurrently() {
        return (messageExtList, context) -> {
            // 这里处理接收到的消息
            messageExtList.forEach(messageExt -> {
                System.out.println("Received message: " + new String(messageExt.getBody()));
            });
            return null;
        };
    }

    /**
     * 配置 RocketMQTemplate
     * Spring Boot 会自动加载 RocketMQ 的配置，不需要手动设置 NameServer 和 ProducerGroup
     */
//    @Bean
//    public RocketMQTemplate rocketMQTemplate() {
//        return new RocketMQTemplate(); // 自动配置会生效
//    }
}