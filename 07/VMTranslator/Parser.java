import java.util.Scanner;

public class Parser {
  private Scanner scanner;
  private String currentCommand;

  public Parser(Scanner scanner) {
    this.scanner = scanner;
  }

  public boolean hasMoreCommands() {
    return scanner.hasNextLine();
  }

  public void advance() {
    if (hasMoreCommands()) {
      currentCommand = scanner.nextLine();

      if (isCommentOrEmptyLine(currentCommand)) {
        advance();
      }
    } 
  }

  public CommandType commandType() {
    String command = currentCommand.split(" ")[0];
      switch (command) {
        case "push":
          return CommandType.C_PUSH;
        case "pop":
          return CommandType.C_POP;
        default:
          return CommandType.C_ARITHMETIC;
      }
  }

  public String arg1() {
    if (commandType() == CommandType.C_RETURN) {
      return null;
    }
    if (commandType() == CommandType.C_ARITHMETIC) {
      return currentCommand;
    }
    return currentCommand.split(" ")[1];
  }

  public int arg2() {
    if (commandHasSecondArg(commandType())) {
      return Integer.valueOf(currentCommand.split(" ")[2]);
    }
    return 0;
  }

  public Boolean isCommentOrEmptyLine(String line) {
    if (line.length() == 0) {
      return true;
    } else if (line.substring(0, 2).equals("//")) {
      return true;
    } else {
      return false;
    }
  }

  public Boolean commandHasSecondArg(CommandType cmd) {
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
}
