package com.suyi.android.util.changeAndroidPackName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Su_UTF_FileReader {

	File olddir;

	public Su_UTF_FileReader(File olddir) {
		// TODO Auto-generated constructor stub
		this.olddir = olddir;
	}

	public List<String> read(String str1, String str2) {
		// TODO Auto-generated method stub
		List<String> list = new LinkedList<String>();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(olddir));
			String str = null;

			while ((str = reader.readLine()) != null) {

				System.out.println(str);
				if (str.contains(str1)) {
					System.out.println(str);
					str = str.replaceAll(str1, str2);
					System.out.println(str);
				}

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
