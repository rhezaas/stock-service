package types

import "time"

// Output ...
type Output struct {
	Date      time.Time
	Code      string
	LowPrice  int
	HighPrice int
}
