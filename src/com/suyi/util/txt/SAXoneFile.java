package com.suyi.util.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXoneFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//

		String dir[] = { "C:/Users/suweiguang/Desktop/新建文本文档.txt",
				};

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
					
					
//					System.out.println(str);
					if (str.contains("\t")) {
//						 System.out.println(str);
						String ss[] = str.split("\t");
						Integer i=0;
						
						for(String s:ss){
							if(i==0)
							System.out.print("amap.put(\""+s+"\",");
							
							if(i==1)
								System.out.println("\""+s+"\");");
							
//							if(i==2)
//								System.out.println(s);
							i++;
							if(i==3)break;
						}
//						String sss[] = ss[0].split(" ");
//						System.out.println(sss);
//						String key = sss[0];
//						String iii = sss[1];

						
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
