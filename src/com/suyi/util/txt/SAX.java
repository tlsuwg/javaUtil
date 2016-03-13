package com.suyi.util.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SAX {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File olddir = new File("D://ss.txt");

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(olddir));
			String str = null;

			while ((str = reader.readLine()) != null) {

				// 199cc/upload/app/201305/193068b5-d365-4578-947c-4692fb6f692e.apk
				// System.out.println(str);

				// map.put("", "");

				if (str.contains("option")) {
					String ss[] = str.split(">");
					
					System.out.print("\""+ss[1].replace("</option", "")+"\""+",");

//					System.out.println("map.put(" + ss[0] + ", \"" + ss[1].replace(".apk", "")
//							+ "\");");
				}
			}
			reader.close();
			reader = null;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
