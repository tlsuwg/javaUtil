package com.suyi.util.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SAXoneFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//

		String dir[] = { "C:/Users/suweiguang/Desktop/ss.txt", };

		LinkedList<String> infos = new LinkedList<String>();

		for (String in : dir) {

			File olddir = new File(in);
			System.out.println(olddir.getName());

			int all = 0;
			List<Integer> list = new ArrayList<Integer>();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(olddir));
				String str = null;

				while ((str = reader.readLine()) != null) {

					
					if(str.startsWith("url:")){

						System.out.println(str);
					}
					
				}

//				if (list.size() > 0)
//					System.out.println("平均耗时:" + all / list.size());

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
}
