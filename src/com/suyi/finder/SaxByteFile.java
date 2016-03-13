package com.suyi.finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class SaxByteFile {

	static HashMap map = new HashMap();
	static HashMap map1 = new HashMap();

	static HashMap map2 = new HashMap();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File file = new File(
				"E:/android_su_app_java/MemoryCacheForCallforAllLocclient_apk3_UTF/src/com/siyi/hongwai/manger/3.txt");

		HashMap<String, String> map = new HashMap<String, String>();

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);

				sax(tempString, map);

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		TreeSet set = new TreeSet();
		//
		set.addAll(map.keySet());
		//
		//
		//
		// System.out.println();
		// System.out.println();
		// System.out.println();

		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {

			Object key = iterator.next();

			// System.out.println(key + "     " + map.get(key));

			System.out.println("map4.put(" + key + ",     \"" + map.get(key)
					+ "\");");

			// if(!map.containsKey(key)){
			// System.out.println("map.put("+key + ",     \"" +
			// map.get(key)+"\");");
			// }

			// System.out.println(key+"      "+map1.get(key)+"     "+map1.get(key));

			// System.out.println("map3.put(" + key + ",new String[]{     \""
			// + map.get(key) + "\", \"" + map1.get(key) + "\"});");

		}

	}

	private static void sax(String tempString, HashMap<String, String> map2) {
		// TODO Auto-generated method stub

		// System.out.println("all=="+tempString.trim().replace("<enum name=",
		// ""));

		String ss[] = tempString.trim().replace("<enum name=", "")
				.split("value=");

		// System.out.println(ss[0].replace("\"", ""));
		// System.out.println(ss[1].replace("/>", "").replace("\"", ""));

		map2.put(ss[1].replace("/>", "").replace("\"", ""),
				ss[0].replace("\"", ""));

	}

}
