package com.suyi.finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class findMain {

	// cd data/data/com.tiqiaa.icontrol

	public static TreeSet set;
	public static HashMap map;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// File d = new File("E:/android_su_app/PINGke????/src");
		// File d = new File("E:/android_su_app/PINGke????/res/layout");
		// File d = new File("E://Java//fanbianyi//bianyi3//apktools//ApkTool");
		File d = new File("E:/android/workspace/jdsdk_src");
		
//		 File d = new File("C:\\Users\\suwg\\Desktop\\classes_dex2jarfor叔叔.src");

		findFile(d);
	}

	private static void findFile(File f) {

		if (f.isDirectory()) {
			File fs[] = f.listFiles();
			if(fs==null)return;
			for (File ff : fs) {
				findFile(ff);
			}
		} else {
			if (f.getAbsolutePath().endsWith("java")) {
				gethttp(f);
			}
		}

	}

	private static void gethttp(File f) {
		// TODO Auto-generated method stub

		// System.out.println(f.getAbsolutePath());

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line = null;
			while (true) {
				

				line = br.readLine();

				if (line == null) {
					br.close();
					break;
				}

				// System.out.println("=="+line);

				if (line.contains("import android.webkit") )

				// if ((line.contains("localLayoutParams1.flags"))
				// ||line.contains("localLayoutParams")
				{
					System.err.println(f.getAbsolutePath() + "==" + line);

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
