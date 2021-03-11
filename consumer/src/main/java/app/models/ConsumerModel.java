package app.models;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import app.dto.Stock;
import common.Kafka;
import common.Log;

public class ConsumerModel {
    private Kafka kafka;
    private Consumer<Long, String> consumer;
    private ConsumerRecords<Long, String> records;

    public ConsumerModel() {
        this.kafka = new Kafka();
        this.consumer = this.kafka.createCustomer("Consumer");
    }

    public void subscribe(String topic) {
        this.consumer.subscribe(Collections.singleton(topic));
    }

    public void getMessage(java.util.function.Consumer<Stock> message) {
        Log.info("Listening Message....");
        while (true) {
            this.records = this.consumer.poll(Duration.ofMillis(1000));

            this.records.forEach(record -> {
                message.accept(this.parseMessage(record.value()));
            });

            this.consumer.commitAsync();
        }
    }

    private Stock parseMessage(String message) {
        // parse message
        Pattern pattern = Pattern.compile("[^\\|;]+");
        Matcher matcher = pattern.matcher(message);

        Stock stock = null;

        // matches array
        List<String> matches = new ArrayList<>();        

        while (matcher.find()) {
            matches.add(matcher.group(0));
        }

        try {
            stock = new Stock(
                new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").parse(matches.get(0)),
                matches.get(1),
                Integer.parseInt(matches.get(2)),
                Integer.parseInt(matches.get(3))
            );
        } catch (Exception err) {
            Log.error(err.getMessage());
        }

        return stock;
    }
}
