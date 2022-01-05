package kafka;

import java.io.IOException;
import java.util.Properties;
import java.time.Duration;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

public class Kafka {

    public static void main(String[] args) throws InterruptedException, IOException {

        String topicName = "creditsTopic";
        String topicName2 = "paymentsTopic";
        String outtopicname = "InfoTopics";
        java.util.Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application-c");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.Long().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        /* 7. Get the credit per client */
        KStream<String, String> creditlines = builder.stream(topicName);
        KTable<String, Double> creditPerClient = creditlines
                .mapValues(v -> getCredit(v))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
                .reduce((v1, v2) -> v1 + v2);
        creditPerClient.toStream().to("creditPerClient", Produced.with(Serdes.String(), Serdes.Double()));

        /* 8. Get the payments per client. */
        KStream<String, String> paymentlines = builder.stream(topicName2);
        KTable<String, Double> paymentPerClient = paymentlines
                .mapValues(v -> getPayments(v))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
                .reduce((v1, v2) -> v1 + v2);
        paymentPerClient.toStream().to("paymentPerClient", Produced.with(Serdes.String(), Serdes.Double()));

        /*9.  Get the current balance of a client. */
        KTable<String, Double> joined = creditPerClient.join(paymentPerClient, (leftValue, rightValue) -> leftValue - rightValue);
        joined.toStream().to("balancePerClient", Produced.with(Serdes.String(), Serdes.Double()));

        /* 10.  Get the total (i.e., sum of all persons) credits.*/
        KTable<String, Double> totalCredits = creditlines.selectKey((old,newkey) -> "total")
                .mapValues(v -> getCredit(v))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
                .reduce((v1, v2) -> v1 + v2);
        totalCredits.toStream().to("totalCredits", Produced.with(Serdes.String(), Serdes.Double()));

        /* 11. Get the total payments */
        KTable<String, Double> totalPayments = paymentlines.selectKey((old,newkey) -> "total")
                .mapValues(v -> getPayments(v))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
                .reduce((v1, v2) -> v1 + v2);
        totalPayments.toStream().to("totalPayments", Produced.with(Serdes.String(), Serdes.Double()));

        // 12. Get the total balance
        joined = totalCredits.join(totalPayments, (leftValue, rightValue) -> leftValue - rightValue);
        joined.toStream().to("totalBalances", Produced.with(Serdes.String(), Serdes.Double()));

        /*13. Compute the bill for each client for the last month*/
        Duration windowSizeMs = Duration.ofMinutes(5);
        KTable<Windowed<String>, Double> monthBill = creditlines
                .mapValues(v -> getPayments(v))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
                .windowedBy(TimeWindows.of(windowSizeMs).advanceBy(windowSizeMs))
                .reduce((aggval, newval) -> aggval + newval, Materialized.as("soma"));
        monthBill.toStream((wk, v) -> wk.key()).map((k, v) -> new KeyValue<>(k, "" + k + "-->" + v)).to("monthBillTopic", Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
    public static Double getCredit(String value){
        Gson gson = new Gson();
        DTOcredits d = gson.fromJson(value, DTOcredits.class);
        Double credits = d.getValue();
        return credits;
    }
    public static Double getPayments(String value){
        Gson gson = new Gson();
        DTOpayments d = gson.fromJson(value, DTOpayments.class);
        Double Result = d.getValue();

        return Result;
    }
    public static Double getBalance(String value){
        Gson gson = new Gson();
        DTObalance d = gson.fromJson(value, DTObalance.class);
        Double balance = d.getValue();
        return balance;
    }
}
