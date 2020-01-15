package com.weibo.test.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class KafkaConsumerTest implements Runnable {

    private String topic;
    private KafkaConsumer kafkaConsumer;

    public KafkaConsumerTest(KafkaConsumer consumer) {
        this.kafkaConsumer = consumer;
    }


    @Override
    public void run() {


        while (true) {
            ConsumerRecords<byte[], byte[]> records = kafkaConsumer.poll(100);
            /*for (ConsumerRecord<byte[], byte[]> record : records){

                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), new String(record.value()).toString());
            }*/
        }
    }

    private static void consumerStream() {
        Properties originalProps = new Properties();
        originalProps.put("zookeeper.connect", "10.77.6.222:8902");

        //group 代表一个消费组
        originalProps.put("group.id", "hashleaf-group1");

        //zk连接超时时间
        originalProps.put("zookeeper.session.timeout.ms", "10000");
        //zk同步时间
        originalProps.put("zookeeper.sync.time.ms", "200");
        //自动提交间隔时间
        originalProps.put("auto.commit.interval.ms", "1000");
        //消息日志自动偏移量,防止宕机后数据无法读取
        originalProps.put("auto.offset.reset", "smallest");
        //序列化类
        originalProps.put("serializer.class", "kafka.serializer.StringEncoder");
        ConsumerConnector _consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(originalProps));
        final Map<String, List<KafkaStream<byte[], byte[]>>> streams = _consumer.createMessageStreams(Collections.singletonMap("fanservice.kafka.click.topic", 1));
        ConsumerIterator<byte[], byte[]> _iterator = streams.get("fanservice.kafka.click.topic").get(0).iterator();
        while (_iterator.hasNext()) {
            MessageAndMetadata<byte[], byte[]> message = _iterator.next();
            System.out.println(new String(message.message()).toString());
        }
    }

    public static void streamTest() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application2");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream("SendWords");
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> {
                    return Arrays.asList(textLine.toLowerCase().split("\\W+"));
                })
                .groupBy((key, word) -> {
                    try {
                        System.out.println("key is =>" + Serdes.Integer().deserializer().deserialize("SendWords", key.getBytes()));
                    } catch (Exception e) {

                    }
                    System.out.println("value is =>" + word);
                    return word;
                })
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
        wordCounts.toStream().to("CountSendWordsNum", Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
    }


    public static void main(String[] args) {
        KafkaConsumer consumer = createConsumer();
        consumer.subscribe(Arrays.asList("write.msg.kafka.test"));
        KafkaConsumerTest kafkaConsumerTest = new KafkaConsumerTest(consumer);
        ExecutorService es = Executors.newFixedThreadPool(1);

        es.execute(() -> {
            while (true) {
                ConsumerRecords<byte[], byte[]> records = consumer.poll(100);
                for (ConsumerRecord<byte[], byte[]> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), new String(record.value()).toString());
                }
            }
        });


        //consumerStream();
        //streamTest();
    }

    private static KafkaConsumer createConsumer() {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(GROUP_ID_CONFIG, "test_group");
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        return new KafkaConsumer(properties);
    }

}