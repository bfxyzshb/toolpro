package com.weibo.test.kafka;

import kafka.javaapi.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class ProducerTest {
    public static void main(String[] args) {

        Properties props = new Properties();

        //broker地址
        props.put("bootstrap.servers", "localhost:9092");

        //请求时候需要验证
        props.put("acks", "all");

        //请求失败时候需要重试
        props.put("retries", 0);

        //内存缓存区大小
        props.put("buffer.memory", 33554432);

        //指定消息key序列化方式
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.ByteArraySerializer");

        //指定消息本身的序列化方式
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.ByteArraySerializer");

        KafkaProducer producer = new KafkaProducer(props);
        Random random =new Random();
        while (true) {
            producer.send(new ProducerRecord<>("pro_test", Integer.toString(random.nextInt()).getBytes(), Integer.toString(random.nextInt()).getBytes()));
        }

    }


}
