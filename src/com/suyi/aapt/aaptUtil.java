package com.suyi.aapt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class aaptUtil {
	
	public  static ArrayList<String> readLins(InputStream inStream)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inStream, "gb2312"));
		ArrayList<String> infos = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			infos.add(line);
		}
		reader.close();
		inStream.close();
		return infos;

	}

	public static void Copy(String oldPath, String newPath) throws IOException {
		new File(newPath).deleteOnExit();
		int byteread = 0;
		File oldfile = new File(oldPath);
		InputStream inStream = new FileInputStream(oldfile);
		FileOutputStream fs = new FileOutputStream(new File(newPath));
		byte[] buffer = new byte[1024];
		while ((byteread = inStream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
		}
		fs.close();
		inStream.close();
	}

	public  static void proErrInfoShow(Process process, String cmd) throws IOException {
		ArrayList<String> infos = readLins(process.getErrorStream());
		if (infos != null && infos.size() > 0) {
			showErrInfo(infos, cmd + "执行出错");
			System.exit(1);
		}
	}

	public   static void showErrInfo(ArrayList<String> infos, String errInfo) {

		if (errInfo != null)
			System.err.println(errInfo);
		if (infos != null)
			for (String err : infos) {
				System.err.println(err);
			}
	}

	public  static void showInfo(ArrayList<String> infos) {

		if (infos != null)
			for (String err : infos) {
				System.out.println("___" + err);
			}
	}
	

	public  static void aaptRemoveOneFile(String cmd_add, File baseDir)
			throws IOException, InterruptedException {
		String cmdadd = "aapt r " + cmd_add;
		System.out.println("cmd:" + cmdadd);
		Process process = Runtime.getRuntime().exec(cmdadd, null, baseDir);
		proErrInfoShow(process, cmdadd);
		ArrayList<String> infos = readLins(process.getInputStream());
		showInfo(infos);
		process.waitFor();
	}
	
	
	
	public  static void aaptAddOneFile(String cmd_add, File baseDir)
			throws IOException, InterruptedException {
		String cmdadd = "aapt a " + cmd_add;
		System.out.println("cmd:" + cmdadd);
		Process process = Runtime.getRuntime().exec(cmdadd, null, baseDir);
		proErrInfoShow(process, cmdadd);
		ArrayList<String> infos = readLins(process.getInputStream());
		showInfo(infos);
		process.waitFor();

	}

}
