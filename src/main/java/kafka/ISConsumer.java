package kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ISConsumer {

    public static void main(String[] args) throws Exception {

        String paymentsTopic = "PaymentsTopic";
        String creditsTopic = "CreditsTopic";
        String resultPayment = "resultPayment";
        String resultCredit = "resultCredit";

        // create instance for properties to access producer configs
        Properties props = new Properties();
        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.LongDeserializer");
        //capitulo 7.3
        Consumer<String, Long> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(creditsTopic));
        //consumer.close();
           try {
            while (true) {
                ConsumerRecords<String, Long> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, Long> record : records) {
                    System.out.println(record.key() + " => " + record.value());
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            //consumer.close();
        }
    }
}