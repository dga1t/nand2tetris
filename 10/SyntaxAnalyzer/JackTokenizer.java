import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

// String[] keywords = {'class','constructor','function','method','field','static','var','int','char','boolean','void','true','false','null','this','let','do','if','else','while','return'};
// String[] symbols = {'{', '}', '[', ']', '(', ')', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~'}

public enum TokenType {
  KEYWORD,
  SYMBOL,
  IDENTIFIER,
  INT_CONST,
  STRING_CONST,
}

// 1. ignore the whitespace
// 2. advancing the input one token at a time
// 3. geting the value and type of the current token 
public class JackTokenizer {
  private PrintWriter printWriter;
  private String fileName;
  private String currentToken;

  public JackTokenizer(File file) {
    fileName = file.getName();
  }

  public Boolean hasMoreTokens() {}

  // advances one char at a time and if it finds a symbol,
  // then check all the previous characters in the keywords arr - if its not there,
  // then it is an identifier
  public void advance() {}

  // returns the type of the currentToken as a constant
  public String tokenType() {}

  // returns the keyword which is the current token as a constant (called only if tokenType is a KEYWORD)
  public String keyWord() {}
  public String symbol() {}
  public String identifier() {}
  public String intVal() {}
  public String strinVal() {}

}
