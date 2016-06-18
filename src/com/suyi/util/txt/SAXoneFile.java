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

		String dir[] = { "C:/Users/suweiguang/Desktop/22.txt", };

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

				int time = 0;
				while ((str = reader.readLine()) != null) {
					str = str.trim().replace(";", "");
					
					

					// String n=str.replace(".", "");
					// System.out.println(n+"="+str+";");
					// System.out.print(" "+n+",");
					if (str.contains("=")) {
//						System.out.println(str);
						String ss[] = str.split("=");

						String ii = ss[1];
						String ssss[] = ii.split("\\.");
//						System.out.println(ssss[2]);
						String ij="";
						if(ssss[1].equals("id")){
					 ij=	ss[0]+"="+"SuRes.getSingleton().getResID(\""+ssss[2]+"\");";
						}else{
							 ij=	ss[0]+"="+"SuRes.getSingleton().getResLayOut(\""+ssss[2]+"\");";
						}
//						Rlayoutneterror_layout=SuRes.getSingleton().getResLayOut("neterror_layout");
					
					System.out.println(ij);

						// Integer i=0;
						//
						// System.out.println("nameMap.put(\""+ss[1]+" \",\""+ss[0]+"\");");
						//
						// // for(String s:ss){
						// // s=s.trim();
						// // if(!"".equals(s)){
						// // System.out.println(s);
						// // infos.add(s);
						// // }
						// // }
					}
				}

				if (list.size() > 0)
					System.out.println("平均耗时:" + all / list.size());

				// for(int i=0;i<infos.size();i+=2){
				// System.out.println("nameMap.put(\""+infos.get(i)+" \",\""+infos.get(i+1)+"\");");
				// }

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
