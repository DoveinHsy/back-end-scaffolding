package org.xiaoxingbomei.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * kafka
 */
@Configuration
public class KafkaConsumerConfig
{
    @Value("${apollo.kafka.broker}")
    private String broker;

    @Value("${apollo.kafka.groupId}")
    private String groupId;

    @Bean
    public KafkaConsumer<String, String> kafkaConsumer() {
        // 配置消费者属性
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker); // Kafka集群地址
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group"); // 消费者组ID
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // key反序列化方式
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // value反序列化方式
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 自动重置消费偏移量的位置（earliest: 从最早的消息开始消费）

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // consumer.subscribe(Arrays.asList("test-topic")); // 订阅指定的topic

        return consumer;
    }

}
