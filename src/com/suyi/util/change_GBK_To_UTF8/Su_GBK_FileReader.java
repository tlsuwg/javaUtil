package com.suyi.util.change_GBK_To_UTF8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Su_GBK_FileReader {

	File olddir;

	public Su_GBK_FileReader(File olddir) {
		// TODO Auto-generated constructor stub
		this.olddir = olddir;
	}

	public List<String> read() {
		// TODO Auto-generated method stub
		List<String> list = new LinkedList<String>();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(olddir));
			String str = null;

			while ((str = reader.readLine()) != null) {

//				 System.out.println("����"+str);
//				str = new String(str.getBytes(), "GBK");

//				System.out.println("����" + str);

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Su_GBK_FileReader su=new Su_GBK_FileReader(
				new File(
						"E://android_su_app//pdouADNew3.4test//src//com//suyi//common//utility//SharedPreferencesControl.java"));
		su.read();
	}

}
