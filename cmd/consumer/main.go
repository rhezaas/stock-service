package main

import (
	"stock-services/common"
	"stock-services/models"
	"stock-services/types"
)

func main() {
	// kafka consumer
	kafka := common.Kafka{}.NewConfig()
	kafkaConsumer, _ := kafka.SetConsumer()
	consumer := &models.Consumer{Kafka: kafkaConsumer}

	// data processor
	processor := &models.Processor{
		Datas:         []types.Stock{},
		DataChannel:   make(chan (types.Stock)),
		OutputChannel: make(chan ([]types.Output)),
	}

	// kafka initial setup
	consumer.Init()
	consumer.Subscribe([]string{"stockPrice"})

	// background data processor
	go processor.ProcessData()
	go processor.WriteData()

	// kafka message getter
	consumer.GetMessage(func(data types.Stock) {
		processor.DataChannel <- data
	})

	consumer.StartConsuming()
}
