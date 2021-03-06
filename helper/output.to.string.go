package helper

import (
	"stock-services/types"
	"strconv"
)

// OutputToString output data helper
func OutputToString(output types.Output) string {
	return output.Date.Format("03:04:00") + "|" +
		output.Code + "|" +
		"high;" + strconv.Itoa(output.HighPrice) + "|" +
		"low;" + strconv.Itoa(output.LowPrice)
}
