package com.suyi.finder.srcfindhebing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainHebing2 {

	static String dir = "E:/android/workspace/SmartObject/httpcomponents-client-4.5.3-src/httpcomponents-client-4.5.3/";

	static String nameCore = "apache";

	static String targetDir = "E:/android/workspace/SmartObject/httpcomponents-client-4.5.3-src/httpcomponents-client-4.5.3/allsrc/org/apache";

	public static void main(String args[]) {
		
		System.out.println(nameCore);
		findCopy(new File(dir));
	}

	private static void findCopy(File f) {
		// TODO Auto-generated method stub
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (File ff : fs) {
				findCopy(ff);
			}
		} else {
			String namecc=f.getAbsolutePath();
			
			if(namecc.contains(nameCore)&&namecc.contains("src")&&namecc.contains("main")&&!namecc.contains("java-deprecated")){
			
				
//				int index=namecc.lastIndexOf(nameCore);
//				System.out.println(index);
				
				
				String [] cc=namecc.split(nameCore);
				if(cc!=null&&cc.length>=2){
					String end=cc[1];
					
					System.err.println("old:"+namecc);
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(end);
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					String to=targetDir+end;
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					System.err.println("copy to:"+to);
					
//					System.out.println();
					
					Copy(namecc,to);
				}
				
				
				
			}

		}

	}

	public static void Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			
			File ne=new File(newPath);
			ne.getParentFile().mkdirs();
			ne.createNewFile();
			
			
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}
}
