package at.tewan.udf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class UDF {
	
	/**Returns the value of the key in the .udf file specified*/
	public static String getString(File file, String key) {
		if(isFileValid(file)) {
			
			for(int i = 0; i < getLineCount(file); i++) {
				try {
					
					// Read line from file at line i
					String currentLine = Files.readAllLines(file.toPath()).get(i);
					
					// Split the string 
					String[] splits = currentLine.split(": ");
					
					// Check if the String was split in 2 parts
					if(splits.length == 2) {
						
						// Check if the first part of the string is equal to the key
						if(splits[0].equals(key)) {
							return splits[1];
						}
					
					// Invalid syntax
					}else if(splits.length > 2){
						error(ErrorType.unvalidCharacter, file, i + "");
						return null;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			error(ErrorType.keyNotFound, file, null);
			
		}else {
			error(ErrorType.unvalidFile, file, null);
		}
		return null;
	}
	
	public static ArrayList<String> getArray(File file, String key) {
		if(isFileValid(file)) {
			
			for(int i = 0; i < getLineCount(file); i++) {
				try {
					
					// Read line from file at line i
					String currentLine = Files.readAllLines(file.toPath()).get(i);
					
					if(!currentLine.contains(": ") && currentLine.contains("{")) {
						
						// Remove everything after { 
						currentLine = currentLine.split("\\{")[0];
						
						// Remove whitespace
						currentLine = currentLine.replaceAll(" ", "");
						
						if(currentLine.equals(key)) {
							ArrayList<String> array = new ArrayList<>();
							
							for(int j = i + 1; j < Files.readAllLines(file.toPath()).size(); j++) {
								String line = Files.readAllLines(file.toPath()).get(j);
								if(line.equals("}")) {
									break;
								}else {
									array.add(line.replaceAll("\t", ""));
								}
							}
							
							return array;
						}
					}
					
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			error(ErrorType.keyNotFound, file, null);
		}
		return null;
	}
	
	/** Returns the line count of the file  */
	private static int getLineCount(File file) {
		int a = 0;
		
		try {
		BufferedReader r = new BufferedReader(new FileReader(file));
		
			while(r.readLine() != null) {
				a++;
			}
			
			r.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	private static boolean isFileValid(File file) {
		return file.exists() && file.isFile() && file.canRead();
	}
	
	private static void error(ErrorType e, File file, String extra) {
		if(e == ErrorType.fileNotFound) {
			System.out.println("Error parsing '"+ file.getPath() + "'! File not found!");
		}
		
		if(e == ErrorType.unvalidCharacter) {
			System.out.println("Error parsing '"+ file.getPath() + "'! Unvalid character : at line " + extra);
		}
		
		if(e == ErrorType.keyNotFound) {
			System.out.println("Error parsing '"+ file.getPath() + "'! Key not found");
		}
	}
}
