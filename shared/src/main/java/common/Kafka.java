package common;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Kafka {
    private final String host = "kafka:9092";
    private Properties defaulProperties;

    public Kafka() {
        this.defaulProperties = new Properties();

        // setup host
        defaulProperties.put(
            CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
            this.host
        );
    }

    public Producer<Long, String> createProducer(String id) {
        // create properties
        Properties props = new Properties();

        // add default properties
        props.putAll(this.defaulProperties);

        // setup initial producer properties
        props.put(ProducerConfig.CLIENT_ID_CONFIG, id);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(props);
    }

    public Consumer<Long, String> createCustomer(String id) {
        // create properties
        Properties props = new Properties();

        // add default properties
        props.putAll(this.defaulProperties);

        // setup initial consumer properties
        props.put(ConsumerConfig.GROUP_ID_CONFIG, id);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(props);
    }
}
