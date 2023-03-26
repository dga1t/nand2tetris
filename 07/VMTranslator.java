import java.util.EnumSet;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

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
  C_UNKNOWN;
}

public class VMTranslator {
  // input - fileName.vm
  // output - fileName.asm

  public static void main(String[] args) throws IOException {
    if (args.length > 0) {
      System.out.println("arg[0] is:");
      System.out.println(args[0]);

      String file = args[0];

      Parser parser = new Parser(file);
      parser.readFile();

      // TODO change file extention to .asm
      // CodeWriter codeWriter = new CodeWriter(file);

    } else {
      System.out.println("Please provide a path to .vm file.");
    }
  }
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
      System.out.println("Reading a text file using Scanner");

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.printf("current line >>> %s\n", line);

        boolean isCommentOrEmptyLine = checkCommentOrEmptyLine(line);
        if (isCommentOrEmptyLine) {
          continue;
        }

        String arg1 = arg1(line);
        System.out.printf("arg1: %s\n", arg1);

        CommandType cmd = commandType(arg1);
        System.out.printf("cmd type: %s\n", cmd);

        boolean hasSecondArg = checkCommandTypeHasSecondArg(cmd);
        System.out.printf("cmd has second arg: %b\n", hasSecondArg);
        if (hasSecondArg) {
          String arg2 = arg2(line);
          System.out.printf("arg2: %s\n", arg2);
        }

        // TODO - pass args to codeWriter

      }

      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error reading file.");
      e.printStackTrace();
    }
  }

  public CommandType commandType(String command) {
    switch (command) {
      case "add":
      case "sub":
      case "neg":
      case "eq":
      case "gt":
      case "lt":
      case "and":
      case "or":
      case "not":
        return CommandType.C_ARITHMETIC;
      case "goto":
        return CommandType.C_GOTO;
      case "push":
        return CommandType.C_PUSH;
      case "pop":
        return CommandType.C_POP;
      case "if":  
        return CommandType.C_IF;
      case "@":
        return CommandType.C_LABEL;
      case "function":
        return CommandType.C_FUNCTION;
      case "return":
        return CommandType.C_RETURN;
      case "call":
        return CommandType.C_CALL;
      default:
        return CommandType.C_UNKNOWN;
    }
  }

  // in case of C_ARITHMETIC the command itself - add, sub, etc..
  // should not be called if the current commands is C_RETURN
  public String arg1(String line) {
    String[] arr = line.split(" ");
    return arr[0];
  }

  // should be called only if the current commands is push/pop/function/call
  public String arg2(String line) {
    String[] arr = line.split(" ");
    return arr[1];
  }

  public Boolean checkCommentOrEmptyLine(String line) {
    if (line.length() == 0) {
      return true;
    } else if (line.substring(0, 2).equals("//")) {
      return true;
    } else {
      return false;
    }
  }

  public Boolean checkCommandTypeHasSecondArg(CommandType cmd) {
    switch(cmd) {
      case C_PUSH:
      case C_POP:
      case C_FUNCTION:
      case C_CALL:
        return true;
      default:
        return false;
    }
  }

  // public boolean hasMoreCommands() {
  //   return true;
  // }

  // public void advance() {
  //   // reads the next command from the input and makes it the current command.
  //   // should be called only if hasMoreCommands() is true
  //   // initially there is no current command
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


