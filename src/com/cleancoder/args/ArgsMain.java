package com.cleancoder.args;

public class ArgsMain {
	  public static void main(String[] args) {
	    try {	
	      //Schema definition. The arguments entered from command prompt are mapped to this schema definition	
	      String schema = "l,p#,d*";
	      Args arg = new Args(schema, args);	      
	      boolean logging = arg.getBoolean('l');
	      int port = arg.getInt('p');
	      String directory = arg.getString('d');
	      //executeApplication(logging, port, directory); --**Changed the function name to a more appropriate term**--
	      printSchemaInformation(logging, port, directory);
	    } catch (ArgsException e) {
	      System.out.printf("Argument error: %s\n", e.errorMessage());
	    }
	  }
      
	  private static void printSchemaInformation(boolean logging, int port, String directory) {
	    System.out.printf("logging is %s, port:%d, directory:%s\n",logging, port, directory);
	  }
	}

