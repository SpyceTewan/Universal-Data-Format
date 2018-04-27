package at.tewan.udf.test;

import java.io.File;
import java.util.ArrayList;

import at.tewan.udf.UDF;

public class Test {
	public static void main(String[] args) {
		ArrayList<String> a = UDF.getArray(new File("C:/users/stefan/desktop/test.udf"), "lyrics");
		
		for(String str : a) {
			System.out.println(str);
		}
	}
}
