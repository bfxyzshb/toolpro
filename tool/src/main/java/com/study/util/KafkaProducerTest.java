package com.study.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Properties;


public class KafkaProducerTest extends Thread {
    public static void main(String[] args) {
        produceIdempotMessage("oasis_relationship_push",buildPushMsg());
    }


    private static Producer buildIdempotProducer(){
        // create instance for properties to access producer configs
        Properties props = new Properties();
        // bootstrap.servers是Kafka集群的IP地址。多个时,使用逗号隔开
        props.put("bootstrap.servers", "10.85.113.5:29092,10.85.113.6:29092,10.85.113.7:29092,10.85.113.8:29092");
        props.put("enable.idempotence",true);
        //If the request fails, the producer can automatically retry,
        props.put("retries", 3);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        props.put("auto.create.topics.enable",true);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        // Kafka消息是以键值对的形式发送,需要设置key和value类型序列化器
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        return producer;
    }
    //发送消息
    public static void produceIdempotMessage(String topic, String message) {
        // 创建Producer
        Producer producer = buildIdempotProducer();
        // 发送消息
        producer.send(new ProducerRecord<String, String>(topic, message));
        producer.flush();
    }

    private static String buildPushMsg() {
        JSONObject pushMsg = new JSONObject();
        JSONArray uids = new JSONArray();
        uids.add("2");
        pushMsg.put("uids", uids);
        pushMsg.put("msg_type", "text");
        pushMsg.put("push_type", "listcast");
        JSONObject msgContent = new JSONObject();
        msgContent.put("text", " 你好");
        msgContent.put("title", "你有一条新消息");
        pushMsg.put("scheme", "oasis://chat?send_uid=1&receive_uid=2");
        pushMsg.put("source", "1197330632");
        pushMsg.put("type", "10");
        return pushMsg.toJSONString();
    }


}