package main

import (
	"fmt"
	"log"
	"stock-services/common"
	"stock-services/models"
)

func main() {
	// file
	file := common.File{}

	err := file.Open("test3.txt")
	file.NewScanner()
	defer file.Close()

	// kafka producer initial setup
	kafka := common.Kafka{}.NewConfig()
	kafkaProducer, err := kafka.SetProducer()
	producer := models.Producer{Kafka: kafkaProducer}
	defer producer.Kafka.Close()

	if err != nil {
		log.Fatalln(err)
	}

	// broadcaster
	fmt.Println("Broadcasting Messgages...")
	for file.Scan() {
		producer.Broadcast("stockPrice", file.ReadLine())
	}
	fmt.Println("Done...")
}
