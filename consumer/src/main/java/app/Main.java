package app;

import app.models.ConsumerModel;
import app.models.ProcessorModel;

public class Main {
    public static void main(String[] args) {
        ConsumerModel consumer = new ConsumerModel();
        ProcessorModel processor = new ProcessorModel();

        consumer.subscribe("stockPrice");

        // // background processor
        new Thread(processor.processData()).start();

        consumer.getMessage(message -> {
            processor.addToProcessQueue(message);
        });
    }
}
