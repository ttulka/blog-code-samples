package main

import (
	"bufio"
	"errors"
	"fmt"
	"io/ioutil"
	"os"
	"regexp"
	"strings"
)

func main() {
	reader := bufio.NewReader(os.Stdin)
	for {
		fmt.Print("🐧 ")

		// read the keyboard input
		input, err := reader.ReadString('\n')
		if err != nil {
			fmt.Fprintln(os.Stderr, err)
		}
		// handle the execution of the cmd
		if err = execCmd(input); err != nil {
			fmt.Fprintln(os.Stderr, err)
		}
	}
}

func execCmd(input string) error {
	// remove spaces
	input = strings.TrimSuffix(input, "\n")
	input = regexp.MustCompile(`\s+`).ReplaceAllString(input, " ")

	// split the input to command and arguments
	args := strings.Split(input, " ")

	if len(args[0]) == 0 {
		return nil
	}

	switch args[0] {
	case "ls":
		if len(args) < 2 {
			return ErrEmptyPath
		}
		return ls(args[1])
	case "cd":
		if len(args) < 2 {
			return ErrEmptyPath
		}
		return cd(args[1])
	case "echo":
		echo(args[1])
	case "exit":
		os.Exit(0)
	case "help":
		help()
	default:
		help()
	}
	return nil
}

func ls(dir string) error {
	files, err := ioutil.ReadDir(dir)
	if err != nil {
		return err
	}
	for _, file := range files {
		if !strings.HasPrefix(file.Name(), ".") {
			fmt.Print(file.Name(), " ")
		}
	}
	fmt.Println()
	return nil
}

func cd(dir string) error {
	return os.Chdir(dir)
}

func echo(str string) {
	fmt.Println(str)
}

func help() {
	fmt.Println("Commands: help, ls, cd, echo, exit")
}

var ErrEmptyPath = errors.New("empty path not supported")
