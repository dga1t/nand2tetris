import java.io.File;
import java.util.ArrayList;

/**
 * Top-level driver that sets up and invokes the other modules.
 *
 * The analyzer program operates on a given source, where source is either a file name (Xxx.jack), or a directory name containing one or more such files.
 * For each source file, the analyzer goes through the following logic:
 * 1. Creates a JackTokenizer from the Xxx.jack input file.
 * 2. Creates an output file called Xxx.xml and prepares it for writing.
 * 3. Uses the CompilationEngine to compile the input JackTokenizer into the output file.”
 */

public class JackAnalyzer {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("use: java JackAnalyzer [filename|directory]");
    } else {
      File inputFile = new File(args[0]);
      File fileOut, tokenFileOut;
      ArrayList<File> jackFiles = new ArrayList<File>();
      String fileOutPath = "", tokenFileOutPath = "";


      if (inputFile.isDirectory()) {
        jackFiles = iterateFiles(inputFile.listFiles());

        if (jackFiles.size() == 0) {
          throw new IllegalArgumentException("There are no jack files in this directory");
        }
      } else {
        String path = inputFile.getAbsolutePath();

        if (!path.endsWith(".jack")) {
          throw new IllegalArgumentException(".jack file is required!");
        }
        jackFiles.add(inputFile);
      }

      for (File f: jackFiles) {
        fileOutPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + ".xml";
        tokenFileOutPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + "T.xml";
        fileOut = new File(fileOutPath);
        tokenFileOut = new File(tokenFileOutPath);

        CompilationEngine compilationEngine = new CompilationEngine(f,fileOut,tokenFileOut);
        compilationEngine.compileClass();

        System.out.println("File created : " + fileOutPath);
        System.out.println("File created : " + tokenFileOutPath);
      }

    }

  }

  private static ArrayList<File> iterateFiles(File[] files) {
    ArrayList<File> result = new ArrayList<File>();

    if (files == null) {
      return result;
    }

    for (File f : files) {
      if (f.getName().endsWith(".jack")) {
        result.add(f);
      }
    }

    return result;
  }
  
}
