package app.models;

import common.Kafka;

import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerModel {
    private Kafka kafka;
    private Producer<Long, String> producer;
    private ProducerRecord<Long, String> record;

    public ProducerModel() {
        this.kafka = new Kafka();
        this.producer = this.kafka.createProducer("Producer");
    }

    public void broadcast(String topic, String message) throws InterruptedException, ExecutionException {
        this.record = new ProducerRecord<Long,String>(topic, message);

        this.producer.send(record).get();
    }
}
