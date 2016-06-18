package com.suyi.util.change_GBK_To_UTF8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class ChangeGBKToUTF8 {
	// 必须保证此类（程序入口为GBK）
	// 保证读流为GBK
	// 上面两个条件正常读出GBK文件
	// 然后使用UTF8写出文件
	// 如果本程序不是GBK无法读出GBK文件

	// static String packagePath = "E:\\android_su_app_java\\IMMemoryCache_3";//
	// 必须是\\

	static String packagePath = "E:\\JavaWorkspace\\JavaUtilGBK";// 必须是\\
	
	
	

	static String newdirstr;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File olddir = new File(packagePath);

		if (!olddir.getAbsolutePath().equals(packagePath)) {
			System.err.println("败家的微软");
			System.err.println("把/改成\\\\");
			System.exit(1);
		}
		File newdir = new File(packagePath + new Date().getTime() + "UTF");
		

		newdir.mkdir();

		if(!olddir.getAbsolutePath().equals(packagePath)){
			System.err.println("�ܼҵ�΢��");
			System.err.println("��/�ĳ�\\\\");
			System.exit(1);
		}

		
		newdirstr = newdir.getAbsolutePath();

		changeUTF(olddir);

	}

	private static void changeUTF(File olddir) {
		// TODO Auto-generated method stub

		if (olddir.isDirectory()) {

			{// copy dir
				String newfilepath = olddir.getAbsolutePath().replace(
						packagePath, newdirstr);
				File newFile = new File(newfilepath);
				newFile.mkdirs();
			}

			File[] files = olddir.listFiles();
			if (files != null && files.length > 0) {
				for (File f : files) {
					changeUTF(f);
				}
			}
		} else if (olddir.isFile()
				&& (!olddir.getAbsolutePath().endsWith(".class"))) {

			if (olddir.getAbsolutePath().endsWith(".java")) {
				if (!isUTF(olddir)) {
					changeGBK_To_UTF8(olddir);
					return;
				}
			}

			copyFileToNewDir(olddir);
		}

	}

	private static void changeGBK_To_UTF8(File olddir) {
		// TODO Auto-generated method stub

		Su_GBKUTF8_FileReader reader = new Su_GBKUTF8_FileReader(olddir,isUTF(olddir));
		List<String> list = reader.read();
		String newfilepath = olddir.getAbsolutePath().replace(packagePath,
				newdirstr);
		File newFile = new File(newfilepath);
		System.out.println("changeGBK_To_UTF8  to" + newfilepath);
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Su_UTF_FileWriter writer = new Su_UTF_FileWriter(newFile);
		writer.writeUTF8(list, newFile);

	}

	private static void copyFileToNewDir(File olddir) {
		// TODO Auto-generated method stub

		String newfilepath = olddir.getAbsolutePath().replace(packagePath,
				newdirstr);

		File newFile = new File(newfilepath);

		// System.out.println("copyFileToNewDir to" + newfilepath);

		try {
			newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileInputStream in;
		try {
			in = new FileInputStream(olddir);
			FileOutputStream out = new FileOutputStream(newFile);
			byte[] buffer = new byte[(int) olddir.length()];

			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.close();
			} else {
				out.write(buffer, 0, ins);
				out.flush();
				in.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static boolean isUTF(File file) {
		// TODO Auto-generated method stub
		InputStream in = null;

		try {
			in = new java.io.FileInputStream(file);

			byte[] b = new byte[3];
			in.read(b);
			in.close();

			if (b[0] == -17 && b[1] == -69 && b[2] == -65)
				return true;//
			else
				return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
