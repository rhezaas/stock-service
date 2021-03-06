package helper

import "stock-services/types"

// FindLatestIndex ...
func FindLatestIndex(outputs []types.Output, data types.Stock) int {
	indexes := []int{}

	for i, output := range outputs {
		if output.Code == data.Code {
			indexes = append(indexes, i)
		}
	}

	if len(indexes) > 0 {
		return indexes[len(indexes)-1]
	}

	return -1
}
