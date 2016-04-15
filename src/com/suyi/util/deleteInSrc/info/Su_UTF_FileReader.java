package com.suyi.util.deleteInSrc.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Su_UTF_FileReader {

	File olddir;

	public Su_UTF_FileReader(File olddir) {
		// TODO Auto-generated constructor stub
		this.olddir = olddir;
	}

	public List<String> read() {
		// TODO Auto-generated method stub
		List<String> list = new LinkedList<String>();

		BufferedReader reader = null;

		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					olddir), "utf-8");
			reader = new BufferedReader(read);

			String str = null;

			while ((str = reader.readLine()) != null) {
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

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// Su_GBK_FileReader su=new Su_GBK_FileReader(
	// new File(
	// "E://android_su_app//pdouADNew3.4test//src//com//suyi//common//utility//SharedPreferencesControl.java"));
	// su.read();
	// }

}
