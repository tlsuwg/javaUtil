package com.suyi.util.change_GBK_To_UTF8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Su_GBKUTF8_FileReader {

	File olddir;
	boolean isUTF8;

	public Su_GBKUTF8_FileReader(File olddir,boolean isUTF8) {
		// TODO Auto-generated constructor stub
		this.olddir = olddir;
		this.isUTF8=isUTF8;
	}

	public List<String> read() {
		// TODO Auto-generated method stub
		List<String> list = new LinkedList<String>();

		BufferedReader reader = null;

		try {
			InputStreamReader read = new InputStreamReader (new FileInputStream(olddir),isUTF8?"UTF-8":"GBK");
			reader = new BufferedReader(read);
			String str = null;
			
			

			while ((str = reader.readLine()) != null) {

//				 System.out.println("锟斤拷锟斤拷"+str);
//				str = new String(str.getBytes(), "GBK");

//				System.out.println("锟斤拷锟斤拷" + str);

				list.add(str);

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

		return list;
	}

	

}
