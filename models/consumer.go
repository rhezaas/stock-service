package models

import (
	"log"
	"regexp"
	"stock-services/types"
	"strconv"
	"time"

	"github.com/Shopify/sarama"
)

// Consumer ...
type Consumer struct {
	Kafka           sarama.Consumer
	chanMessage     chan *sarama.ConsumerMessage
	messageReceiver func(types.Stock)
}

// Init set initial consumer setup
func (consumer *Consumer) Init() *Consumer {
	consumer.messageReceiver = func(data types.Stock) {}
	consumer.chanMessage = make(chan *sarama.ConsumerMessage, 256)

	return consumer
}

// Subscribe to any topic
func (consumer *Consumer) Subscribe(topics []string) {
	for _, topic := range topics {
		partitionList, err := consumer.Kafka.Partitions(topic)
		if err != nil {
			log.Fatal("Unable to get partition got error", err)
			continue
		}
		for _, partition := range partitionList {
			go consumer.consumeMessage(topic, partition, consumer.chanMessage)
		}
	}
}

// StartConsuming from producer
func (consumer *Consumer) StartConsuming() {
	regex := regexp.MustCompile(`[^\|;]+`)

	for {
		select {
		case msg := <-consumer.chanMessage:
			data := regex.FindAllString(string(msg.Value), -1)
			date, _ := time.Parse("2006-Jan-02 03:04:05", data[0])
			prefix, _ := strconv.Atoi(data[2])
			price, _ := strconv.Atoi(data[3])

			stock := types.Stock{
				Date:   date,
				Code:   data[1],
				Prefix: prefix,
				Price:  price,
			}

			consumer.messageReceiver(stock)
		}
	}
}

// GetMessage from producer
func (consumer *Consumer) GetMessage(fn func(types.Stock)) {
	consumer.messageReceiver = fn
}

func (consumer *Consumer) consumeMessage(topic string, partition int32, chanMessage chan *sarama.ConsumerMessage) {
	partitionConsumer, err := consumer.Kafka.ConsumePartition(topic, partition, sarama.OffsetNewest)

	if err != nil {
		log.Fatal("Unable to consume partition ", partition, " got error ", err)
	}

	defer func() {
		if err := partitionConsumer.Close(); err != nil {
			log.Fatal("Unable to close partition ", partition, ": ", err)
		}
	}()

	for {
		select {
		case messages := <-partitionConsumer.Messages():
			chanMessage <- messages
		}
	}
}
