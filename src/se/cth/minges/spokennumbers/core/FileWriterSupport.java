package se.cth.minges.spokennumbers.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Reads and writes data to a txt-file.
 * @author Florian Minges
 */
public class FileWriterSupport {

	/**
	 * For testing purposes only.
	 * @param args
	 */
	public static void main(String[] args) {
		String dir = System.getProperty("user.dir");
		FileWriterSupport.appendToTextFile(dir + System.getProperty("file.separator") + "walla.txt", "22 rätt av 35");
	}
	
	public static String readTextFile(String fileName) {
		  String returnValue = "";
		  FileReader file = null;
		  BufferedReader reader = null;
		  try {
			  file = new FileReader(fileName);
			  reader = new BufferedReader(file);
			  String line = "";
			  while ((line = reader.readLine()) != null) {
		      returnValue += line + System.getProperty("line.separator");
		    }
		  } catch (FileNotFoundException e) {
		      throw new RuntimeException("File not found");
		  } catch (IOException e) {
		      throw new RuntimeException("IO Error occured");
		  } finally {
			  try {
				  reader.close();
			  } catch (IOException ioe) {
				  ioe.printStackTrace();
			  }
		  }
		  
		  return returnValue;
		} 
	
	public static void writeTextFile(String fileName, String s) {
		FileWriter output = null;
		BufferedWriter writer = null;
	    try {
	    	output = new FileWriter(fileName);
    		writer = new BufferedWriter(output);
    		writer.write(s);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	    	try {
	    		writer.close();
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    	}
	    }
	}
	
	public static void appendToTextFile(String fileName, String s) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(FileWriterSupport.readTextFile(fileName));
		} catch (RuntimeException rte) {
			//in case file does not exist yet.
			sb.append("DATE\t\t\t\t\tRAW\t\t SCORE\t\tTIME\t\t\t\t\tCOMMENT");
			sb.append(System.getProperty("line.separator"));
			sb.append("=============================================================");
			sb.append(System.getProperty("line.separator"));
		} 
		
		sb.append(s);
		FileWriterSupport.writeTextFile(fileName,sb.toString());
		
		
	}

}
