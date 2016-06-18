package com.suyi.android.util.changeAndroidPackName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ChangePackageNameMain {

	static String oldPackage="";//
	static String newPackage = "com.suyi.autoapp_6";//
	

	static String packagePath = "E:\\android_su_app\\CommApp";// 程序的path
	static String packagePathAPK = "C:\\Users\\suwg\\Desktop/";// 输出的地址 桌面
	static String names = "tantantu";// apk name
	static String namesend = "V3.4autoAPK";// apk name

	// appdear_2000001_v2.0.1
	static int count = 1;// app需要的数量

	

	public static void main(String[] args) {

		for (int i = 1; i <= count; i++) {
			System.out.println("开始" + i);
			int index =  i;

			changeFile(index);

//			antRelease();
//
//			boolean is = renameTo(index);
//			if (is) {
//				System.out.println("完成." + i + "个apk打包");
//
//				System.err.println("===========================" + i);
//				System.err.println("===========================" + i);
//				System.err.println("===========================" + i);
//				System.err.println("===========================" + i);
//			} else {
//				System.err.println("没有完成." + i + "个apk打包");
//				break;
//			}

		}

		System.err.println("全部完成");

	}
	
	
	

	private static boolean renameTo(int k) {
		// TODO Auto-generated method stub
		File fileapk = new File(packagePath + "/bin/pdouADNew-release.apk");
		File fileapknew = new File(packagePathAPK + "getapks/" + names + "_"
				+ namesend + "_" + k + ".apk");

		if (fileapknew.exists()) {
			fileapknew.deleteOnExit();
			try {
				fileapknew.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return fileapk.renameTo(fileapknew);

	}

	private static void antRelease() {
		// TODO Auto-generated method stub

		System.out.println("开始编译apk");
		Process p;
		try {
			p = Runtime.getRuntime().exec(
					"E:/apache-ant-1.8.2/bin/ant.bat -buildfile " + packagePath
							+ "/build.xml release");

			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("编译" + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void changeFile(int k) {

//		getOldPackageNameInAndroidManifest();

		File file = new File(packagePath);

		changePackageName(file);

	}

	private static void changePackageName(File olddir) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		if (olddir.isDirectory()) {

			// {// copy dir
			// String newfilepath = olddir.getAbsolutePath().replace(
			// packagePath, newdirstr);
			// File newFile = new File(newfilepath);
			// newFile.mkdirs();
			// }

			File[] files = olddir.listFiles();
			if (files != null && files.length > 0) {
				for (File f : files) {
					changePackageName(f);
				}
			}
		} else if (olddir.isFile()
				&& (!olddir.getAbsolutePath().endsWith(".class"))) {

			if (olddir.getAbsolutePath().endsWith(".java")
					|| olddir.getAbsolutePath().endsWith(".xml")) {

				changePackageInFile(olddir);
			}

		}

	}

	private static void changePackageInFile(File olddir) {
		// TODO Auto-generated method stub

		Su_UTF_FileReader reader = new Su_UTF_FileReader(olddir);

		List<String> list = reader.read(oldPackage, newPackage);

		try {
			olddir.createNewFile();// 建立新文件
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Su_UTF_FileWriter writer = new Su_UTF_FileWriter(olddir);

		writer.writeUTF8(list, olddir);

	}

	private static void getOldPackageNameInAndroidManifest() {
		// TODO Auto-generated method stub

		File file = new File(packagePath + "/AndroidManifest.xml");

		if (file == null) {
			System.out.println("没有获取到AndroidManifest.xml");
			System.exit(1);
		} else {

			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(file));
				String str = null;

				while ((str = reader.readLine()) != null) {

					// str=new String(str.getBytes(),"utf-8");

					// System.out.println(str);
					if (str.contains("package=")) {
						oldPackage = str.replaceAll("package=", "");
						oldPackage = oldPackage.replace("\"", "").trim();

						if (oldPackage == null || "".equals(oldPackage)) {
							System.err.println("没有获取到oldPackage");
							System.exit(1);
						} else {
							System.out.println("oldPackage==" + oldPackage);
							
							if(oldPackage.equals(newPackage)){
								
								System.err.println("新旧名字一致");
								System.exit(3);
							}
						}

						break;
					}
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

		}

	}

}
