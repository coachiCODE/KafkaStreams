package kafka;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ISProducer {

    public static void main(String[] args) throws Exception {

        Thread threadCredit = new Thread() {
            public void run() {

                //Assign topicName to string variable
                String topicName = "CreditsTopic";
                // create instance for properties to access producer configs
                Properties props = new Properties();
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
                props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                props.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");

                Producer<String, Long> producer = new KafkaProducer<>(props);

                Random random = new Random();
                int user = random.nextInt(2-1) + 1;
                long credit = ThreadLocalRandom.current().nextLong(1,20000);

                producer.send(new ProducerRecord<String, Long>(topicName, Integer.toString(user), credit));
                System.out.println("Sending message " + (credit) + " to topic " + topicName);
                producer.close();
            }
        };

        Thread paymentThread = new Thread() {
            public void run() {

                //Assign topicName to string variable
                String topicName = "PaymentsTopic";
                // create instance for properties to access producer configs
                Properties props = new Properties();
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
                props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                props.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");

                Producer<String, Long> producer = new KafkaProducer<>(props);

                Random random = new Random();
                int user = random.nextInt(2-1) + 1;
                long payment = ThreadLocalRandom.current().nextLong(1,20000);
                producer.send(new ProducerRecord<String, Long>(topicName, Integer.toString(user), payment));
                System.out.println("Sending message " + (payment) + " to topic " + topicName);
                producer.close();
            }
        };
        threadCredit.start();
        paymentThread.start();
    }
}