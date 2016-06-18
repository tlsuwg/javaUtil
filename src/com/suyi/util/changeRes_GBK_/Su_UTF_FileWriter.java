package com.suyi.util.changeRes_GBK_;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Su_UTF_FileWriter {

	File olddir;

	public Su_UTF_FileWriter(File olddir) {
		// TODO Auto-generated constructor stub
		this.olddir = olddir;
	}

	public  void writeUTF8(List<String> list, File newFile) {
		// TODO Auto-generated method stub
		
		try {
//			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(newFile),"UTF-8");
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(newFile),"GBK");
			
			while (list.size() > 0) {
				String str = list.remove(0);
//				System.out.println("鍐欏叆" + str);
				out.write(str+"\n");
			}
		
			out.close();			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
//
//			while (list.size() > 0) {
//				String str = list.remove(0);
//				System.out.println("鍐欏叆" + str);
//				bw.write(str+"\n");
//			}
//
//			bw.close();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	
}
