package models

import (
	"fmt"
	"log"
	"stock-services/common"
	"stock-services/helper"
	"stock-services/types"
)

// Processor ...
type Processor struct {
	Datas         []types.Stock
	DataChannel   chan types.Stock
	OutputChannel chan []types.Output
}

// ProcessData ...
func (processor *Processor) ProcessData() {
	fmt.Println("Processing Data in background...")

	for {
		select {
		case data := <-processor.DataChannel:
			processor.Datas = append(processor.Datas, data)
			processor.OutputChannel <- processor.process()
		}
	}
}

func (processor *Processor) process() []types.Output {
	datas := processor.Datas
	outputs := []types.Output{}

	for _, data := range datas {
		index := helper.FindLatestIndex(outputs, data)

		if index > -1 && data.Date.Minute() == outputs[index].Date.Minute() {
			outputs[index].LowPrice = helper.Min(data.Price, outputs[index].LowPrice)
			outputs[index].HighPrice = helper.Max(data.Price, outputs[index].HighPrice)
		} else {
			outputs = append(outputs, types.Output{
				Date:      data.Date,
				Code:      data.Code,
				LowPrice:  data.Price,
				HighPrice: data.Price,
			})
		}
	}

	return outputs
}

// WriteData ...
func (processor Processor) WriteData() {
	for {
		select {
		case outputs := <-processor.OutputChannel:
			processor.write(outputs)
		}
	}
}

func (processor Processor) write(outputs []types.Output) {
	file := common.File{}
	defer file.Close()

	err := file.Open("output.txt")

	if err != nil {
		file.Create("output.txt")
	}

	writer := file.NewWriter()

	for i, ouput := range outputs {
		data := helper.OutputToString(ouput)

		if i < len(outputs)-1 {
			data += "\n"
		}

		writer.WriteString(data)
	}

	err = writer.Flush()

	if err != nil {
		log.Fatal(err)
	}
}
