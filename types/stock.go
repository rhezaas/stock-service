package types

import "time"

// Stock ...
type Stock struct {
	Date   time.Time
	Code   string
	Prefix int // the 56 one, i don't really know what is it, just call it prefix for now
	Price  int
}
