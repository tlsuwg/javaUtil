package com.suyi.util.changeFileName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChangeFileName {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String args1[] = new String[] { "fghjk", "vm" };
		File olddir = new File("C:/Users/suwg/Desktop/腾讯X5浏览服务");
		File[] fs = olddir.listFiles();
		int i = 1;
		for (File f : fs) {
//			String kk = null;
//			if (i < 10)
//				kk = "0" + i;
//			else {
//				kk = i + "";
//			}
//			String newName = f.getAbsolutePath().replace(kk, args1[i]);
			
			
			changName(f, f.getAbsolutePath()+".jpg");
			f.delete();
			// changName1(f,newName);
			

			i++;
		}
	}

	private static void changName1(File f, String newName) {
		// TODO Auto-generated method stub
		f.renameTo(new File(newName));
	}

	private static void changName(File f, String newName) {
		// TODO Auto-generated method stub
		try {
			new File(newName).createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Copy(f, newName);

	}

	public static void Copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
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
