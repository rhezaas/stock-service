package models

import (
	"log"

	"github.com/Shopify/sarama"
)

// Producer ...
type Producer struct {
	Kafka sarama.SyncProducer
}

// Broadcast ...
func (producer Producer) Broadcast(topic string, message string) error {
	kafkaMsg := &sarama.ProducerMessage{
		Topic: topic,
		Value: sarama.StringEncoder(message),
	}

	_, _, err := producer.Kafka.SendMessage(kafkaMsg)

	if err != nil {
		log.Fatalln("Send message error: ", err)
		return err
	}

	return nil
}
