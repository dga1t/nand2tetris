import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class CodeWriter {
  private PrintWriter printWriter;
  private String fileName;

  private int labelCount = 0;

  public CodeWriter(File file) {
    printWriter = null;
    File outputFile = new File(file.getAbsolutePath().split(".vm")[0] + ".asm");
    try {
      printWriter = new PrintWriter(new FileWriter(outputFile));
      fileName = file.getName();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setFileName(String fName) {
    fileName = fName;
  }

  public void writeArithmetic(String command) {
    printWriter.printf("// %s\n", command);

    switch (command) {
      case "add":
        popStackToD();
        decrementStackPointer();
        loadStackPointerToA();
        printWriter.println("M=D+M");
        incrementStackPointer();
        break;
      case "sub":
        popStackToD();
        decrementStackPointer();
        loadStackPointerToA();
        printWriter.println("M=M-D");
        incrementStackPointer();
        break;
      case "neg":
        decrementStackPointer();
        loadStackPointerToA();
        printWriter.println("M=-M");
        incrementStackPointer();
        break;
      case "eq":
        writeCompareLogic("JEQ");
        break;
      case "gt":
        writeCompareLogic("JGT");
        break;
      case "lt":
        writeCompareLogic("JLT");
        break;
      case "and":
        popStackToD();
        decrementStackPointer();
        loadStackPointerToA();
        printWriter.println("M=D&M");
        incrementStackPointer();
        break;
      case "or":
        popStackToD();
        decrementStackPointer();
        loadStackPointerToA();
        printWriter.println("M=D|M");
        incrementStackPointer();
        break;
      case "not":
        decrementStackPointer();
        loadStackPointerToA();
        printWriter.println("M=!M");
        incrementStackPointer();
        break;
    }

  }

  public void writePushPop(CommandType commandType, String segment, int index) {
    switch (commandType) {
      case C_PUSH:
        printWriter.printf("// push %s %d\n", segment, index);
        switch (segment) {
          case "constant":
            // store value in D
            printWriter.println("@" + index);
            printWriter.println("D=A");
            break;
          case "local":
            loadSegment("LCL", index);
            printWriter.println("D=M");
            break;
          case "argument":
            loadSegment("ARG", index);
            printWriter.println("D=M");
            break;
          case "this":
            loadSegment("THIS", index);
            printWriter.println("D=M");
            break;
          case "that":
            loadSegment("THAT", index);
            printWriter.println("D=M");
            break;
          case "pointer":
            printWriter.println("@R" + String.valueOf(3 + index));
            printWriter.println("D=M");
            break;
          case "temp":
            printWriter.println("@R" + String.valueOf(5 + index));
            printWriter.println("D=M");
            break;
          case "static":
            printWriter.println("@" + fileName.split("\\.")[0] + String.valueOf(index));
            printWriter.println("D=M");
        }
        pushDToStack();
        break;
      case C_POP:
        printWriter.printf("// pop %s %d\n", segment, index);
        switch (segment) {
          case "constant":
            printWriter.println("@" + index);
            break;
          case "local":
            loadSegment("LCL", index);
            break;
          case "argument":
            loadSegment("ARG", index);
            break;
          case "this":
            loadSegment("THIS", index);
            break;
          case "that":
            loadSegment("THAT", index);
            break;
          case "pointer":
            printWriter.println("@R" + String.valueOf(3 + index));
            break;
          case "temp":
            printWriter.println("@R" + String.valueOf(5 + index));
            break;
          case "static":
            printWriter.println("@" + fileName.split("\\.")[0] + String.valueOf(index));
            break;

        }
        printWriter.println("D=A");
        printWriter.println("@R13");
        printWriter.println("M=D");
        popStackToD();
        printWriter.println("@R13");
        printWriter.println("A=M");
        printWriter.println("M=D");
        break;
    }
  }

  public void close() {
    printWriter.close();
  }

  private void incrementStackPointer() {
    printWriter.println("@SP");
    printWriter.println("M=M+1");
  }

  private void decrementStackPointer() {
    printWriter.println("@SP");
    printWriter.println("M=M-1");
  }

  private void popStackToD() {
    decrementStackPointer();
    printWriter.println("A=M");
    printWriter.println("D=M");
  }

  private void pushDToStack() {
    loadStackPointerToA();
    printWriter.println("M=D");
    incrementStackPointer();
  }

  private void loadStackPointerToA() {
    printWriter.println("@SP");
    printWriter.println("A=M");
  }

  private void writeCompareLogic(String jumpCommand) {
    popStackToD();
    decrementStackPointer();
    loadStackPointerToA();
    printWriter.println("D=M-D");
    printWriter.println("@LABEL" + labelCount);
    printWriter.println("D;" + jumpCommand);
    loadStackPointerToA();
    printWriter.println("M=0");
    printWriter.println("@ENDLABEL" + labelCount);
    printWriter.println("0;JMP");
    printWriter.println("(LABEL" + labelCount + ")");
    loadStackPointerToA();
    printWriter.println("M=-1");
    printWriter.println("(ENDLABEL" + labelCount + ")");
    incrementStackPointer();
    labelCount++;
  }

  private void loadSegment(String segment, int index) {
    printWriter.println("@" + segment);
    printWriter.println("D=M");
    printWriter.println("@" + String.valueOf(index));
    printWriter.println("A=D+A");
  }
}
