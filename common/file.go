package common

import (
	"bufio"
	"os"
)

// File ...
type File struct {
	file    *os.File
	scanner *bufio.Scanner
	writer  *bufio.Writer
}

// Create ...
func (file *File) Create(filename string) error {
	var err error
	file.file, err = os.Create(filename)

	return err
}

// Open ...
func (file *File) Open(filename string) error {
	var err error
	file.file, err = os.OpenFile(filename, os.O_RDWR, os.ModeAppend)

	return err
}

// Close ...
func (file *File) Close() error {
	return file.file.Close()
}

// NewScanner ...
func (file *File) NewScanner() {
	file.scanner = bufio.NewScanner(file.file)
	file.scanner.Split(bufio.ScanLines)
}

// NewWriter ...
func (file *File) NewWriter() *bufio.Writer {
	file.writer = bufio.NewWriter(file.file)

	return file.writer
}

// Scan ...
func (file *File) Scan() bool {
	return file.scanner.Scan()
}

// ReadLine ...
func (file *File) ReadLine() string {
	return file.scanner.Text()
}

// WriteLine ...
func (file *File) WriteLine(text string) error {
	_, err := file.writer.WriteString(text)

	err = file.writer.Flush()

	return err
}
