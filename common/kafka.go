package common

import (
	"time"

	"github.com/Shopify/sarama"
)

// Kafka ...
type Kafka struct {
	config *sarama.Config
}

// NewConfig ...
func (kafka Kafka) NewConfig() Kafka {
	kafka.config = sarama.NewConfig()
	kafka.config.Producer.Return.Successes = true
	kafka.config.Net.WriteTimeout = 5 * time.Second
	kafka.config.Producer.Retry.Max = 0

	return kafka
}

// SetProducer ...
func (kafka Kafka) SetProducer() (sarama.SyncProducer, error) {
	return sarama.NewSyncProducer([]string{"kafka:9092"}, kafka.config)
}

// SetConsumer ...
func (kafka Kafka) SetConsumer() (sarama.Consumer, error) {
	return sarama.NewConsumer([]string{"kafka:9092"}, kafka.config)
}
