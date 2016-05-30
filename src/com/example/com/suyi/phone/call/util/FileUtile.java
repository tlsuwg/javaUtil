/**
 * 
 */
package com.example.com.suyi.phone.call.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author "suwg" 2016Äê5ÔÂ27ÈÕ
 */
public class FileUtile {

	/**
	 * @param list
	 * @param name
	 * @param file
	 */
	public static void saveStrings(LinkedList<String> list, File file,String name) {
		if (list == null || list.size() == 0)
			return;
		File appFile = new File(file, name);
		FileWriter fw = null;
		try {
			fw = new FileWriter(appFile);
			for (String info : list) {
				fw.write(info+"\n");
			}
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}
		}

	}

}
