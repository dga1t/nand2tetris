import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class VMTranslator {
  private static CodeWriter codeWriter;

  public static void main(String[] args) throws IOException {
    File inputFile = new File(args[0]);
    codeWriter = new CodeWriter(inputFile);

    if (inputFile.isDirectory()) {
      iterateFiles(inputFile.listFiles());
    } else {
      translate(inputFile);
    }
    codeWriter.close();
  }

  private static void iterateFiles(File[] files) {
    for (File file : files) {
      if (file.isDirectory()) {
        iterateFiles(file.listFiles());
      } else {
        if (file.getName().endsWith(".vm")) {
          translate(file);
        }
      }
    }
  }

  private static void translate(File file) {
    File outputFile = new File(file.getName().split(".vm")[0] + ".asm");
    Scanner inputScanner = null;

    try {
      inputScanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Parser parser = new Parser(inputScanner);
    codeWriter.setFileName(outputFile.getName());

    while (parser.hasMoreCommands()) {
      parser.advance();
      switch (parser.commandType()) {
        case C_PUSH:
        case C_POP:
          codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
          break;
        case C_ARITHMETIC:
          codeWriter.writeArithmetic(parser.arg1());
          break;
      }
    }
  }
}
