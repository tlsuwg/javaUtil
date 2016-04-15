package com.suyi.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ChangeFileDir {

	public static void main(String args[]) {
		File f = new File(
				"C:/Program Files (x86)/Tencent/QQPCMgr/11.6.17588.206/AndroidServer/1.0.0.512/AndroidServerccccccccccccc.exe");
		File f1 = new File(
				"C:/Program Files (x86)/Tencent/QQPCMgr/11.6.17588.206/AndroidServer/1.0.0.512/AndroidServer.exe");
		f.renameTo(f1);
		
		Copy("C:/Users/suweiguang/Desktop/AndroidServer.exe",
				"C:/Program Files (x86)/Tencent/QQPCMgr/11.6.17588.206/AndroidServer/1.0.0.512/AndroidServer.exe");

	}

	public static void Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
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
