package com.suyi.util.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAX {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//

		String dir[] = { "C:/Users/suweiguang/Desktop/单品log不使用.txt",
				"C:/Users/suweiguang/Desktop/单品log使用.txt" };

		for (String in : dir) {

			File olddir = new File(in);
			System.out.println(olddir.getName());

			int all = 0;
			List<Integer> list = new ArrayList<Integer>();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(olddir));
				String str = null;

				int time = 0;
				while ((str = reader.readLine()) != null) {
					if (str.contains("=2222222222222222222222=")) {
						// System.out.println(str);
						String ss[] = str.split("=2222222222222222222222=");
						String sss[] = ss[0].split("\\): ");
						String iii = sss[1];

						int cc = Integer.parseInt(iii);
						time++;
						if (time == 3)

						{
							System.out.println(cc);
							list.add(cc);
							all += cc;
							time = 0;
						}
					}
				}

				if (list.size() > 0)
					System.out.println("平均耗时:" + all / list.size());

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
