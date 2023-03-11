// package com.journaldev.readfileslinebyline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

enum CommandType {
  C_ARITHMETIC,
  C_FUNCTION,
  C_RETURN,
  C_LABEL,
  C_PUSH,
  C_GOTO,
  C_CALL,
  C_POP,
  C_IF,
}

public class VMTranslator {
  // input - fileName.vm
  // output - fileName.asm

  public static void main(String[] args) {
    if (args.length > 0) {
      System.out.println("arg[0] is:");
      System.out.println(args[0]);

      Parser parser = new Parser(args[0]);
      parser.readFile();

    } else {
      System.out.println("Please provide a path to .vm file.");
    }
  }

  // TODO - consturct Parser
  // TODO - consturct CodeWriter
}

class Parser {
  String inputFile;
  Scanner scanner;
  // int currentCommand = 0;

  public Parser(String fileName) {
    inputFile = fileName;
  }

  public void readFile() {
    try {
      scanner = new Scanner(new File(inputFile));
      System.out.println("Read text file using Scanner");

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error reading file.");
      e.printStackTrace();
    }
  }

  // TODO - read a file argv[1]
  // parse each line into its lexical components
  // ignore all white space and comments

  public boolean hasMoreCommands() {
    return true;
  }

  public void advance() {
    // reads the next command from the input
    // and makes it the current command.
    // should be called only if hasMoreCommands() is true
    // initially there is no current command
  }

  // public String commandType() {
  //   // returns a constant representing the type of the current command
  // }

  // public String arg1() {
  //   // returns the first arg of current command
  //   // in case of C_ARITHMETIC the command itself - add, sub, etc..
  //   // should not be called if the current commands is C_RETURN
  // }

  // public String arg2() {
  //   // returns the second arg of current command
  //   // should be called only if the current commands is push/pop/function/call
  // }

}

class CodeWriter {
  String outputFile;

  // receives string or stream ??
  // opens the outputFile/stream and gets ready to write to it
  public CodeWriter(String fileName) {
    outputFile = fileName;
  }

  public void writeArithmetic(String command) {
    // writes to the output file the assembly code
    // that implements the given arithmetic command

    // most of the work done here
  }

  public void writePushPop(String command, String segment, int index) {
    // writes to the output file the assembly code
    // that implements the given command - either C_PUSH or C_POP

    // rest of the work done here
  }

  public void close() {
    // closes the output file
  }
}
