package com.suyi.finder.filesCom;

import java.io.File;
import java.util.HashMap;

public class findMain {

	public static HashMap mapS = new HashMap();
	public static String d2d = "C:/Users/suweiguang/Desktop/com.jd.jdsdk/module"; // 这个多
	public static String ds = "C:/Users/suweiguang/Desktop/开普勒日志/com.jd.jdsdk/module"; // 这个少

	public static void main(String[] args) {

		File d2 = new File(ds);
		findFile(d2);

		File d = new File(d2d);// shao
		findFilenew(d);

	}

	private static void findFilenew(File f) {

		if (f.isDirectory()) {
			File fs[] = f.listFiles();
			if (fs == null)
				return;
			for (File ff : fs) {
				findFilenew(ff);
			}
		} else {
			String fname = f.getAbsolutePath();
			fname = fname.split("module")[1];
			// System.out.println("wwwwwwwwww"+fname);
			Object oo = mapS.get(fname);
			if (oo == null) {
				System.out.println("no没有" + fname);
			} else {
				long ooo = (Long) oo;
				long oonew = f.length();
				if (ooo != oonew) {
					System.out.println("不一致" + fname);
					System.out.println("文件大小："+ooo + "  " + oonew);
				} else {
					// System.out.println("一致"+fname);/
				}
			}
		}

	}

	private static void findFile(File f) {

		if (f.isDirectory()) {
			File fs[] = f.listFiles();
			if (fs == null)
				return;
			for (File ff : fs) {
				findFile(ff);
			}
		} else {
			String fname = f.getAbsolutePath();
			fname = fname.split("module")[1];
			// System.out.println("sssssssssss"+fname);
			mapS.put(fname, f.length());
		}

	}

}
