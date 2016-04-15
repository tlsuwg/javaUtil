package com.suyi.aapt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ApkAddFiles {
	
//	需要使用默认环境的方式执行命令
//	aapt没有指定源文件 和存放目录的结构
//	使用什么样子的结构就放置在什么样子的结构里面
//	使用到了当前环境变量的方法
	private static final String SECRET_KEY_PATH = "D:/androidAapt/";
	private static final String SECRET_KEY_NAME = "debug.keystore";
	private static final String SECRET_KEY_PASS = "android";
	private static final String SECRET_KEY_ALIAS_PASS = "android";
	private static final String SECRET_KEY_ALIAS = "androiddebugkey";
	private static final String SECRET_KEY_FOR_1_DOT_7 = " -digestalg SHA1 -sigalg MD5withRSA";
	private static final String UNSIGN_FILE_NAME = "a.apk";//签名 或者未签名
	private static final String UNSIGN_FILE_NAME_Temp = "claered_temp.apk";
	private static final String SIGN_FILE_NAME = "signed/";

	String apkPath;
	String apkClearPath;
	List<String> listForDel = new ArrayList<String>();
	File baseDir;

	String[] addFiles = new String[] { "assets/Kepler_jar_2016_04_12_11-15-09.kep" };
	private static final String[] chanles = new String[] { "s", "d", "y" };

	public ApkAddFiles() {
		super();
		apkPath = SECRET_KEY_PATH + UNSIGN_FILE_NAME;
		apkClearPath = SECRET_KEY_PATH + UNSIGN_FILE_NAME_Temp;
		baseDir = new File(SECRET_KEY_PATH);
	}

	public static void main(String args[]) throws IOException {
		ApkAddFiles mApkAddFiles = new ApkAddFiles();
		mApkAddFiles.doThis();
	}

	private void doThis() throws IOException {
		System.out.println("备份apk");
		Copy(apkPath, apkClearPath);
		System.out.println("清除apk");
		getOneClearedApk();
		System.out.println("全部添加需要files");
		addSomeNeedFiles();// 共性需要的

		
		for (String chanle : chanles) {
			String apkName = chanle + ".apk";
			File dir = new File(SECRET_KEY_PATH + SIGN_FILE_NAME);
			String newPathChanle = SECRET_KEY_PATH + SIGN_FILE_NAME + apkName;
			Copy(apkClearPath, newPathChanle);

			// ==========修改渠道文件
			String addFilePath = "Kepler_jar_2016_04_12_11-15-"
					+ (int) (Math.random() * 100) + ".kep";
			File d = new File(SECRET_KEY_PATH + SIGN_FILE_NAME + "assets/");
			if (!d.exists()) {
				d.mkdirs();
			}
			File file = new File(d, addFilePath);
			file.createNewFile();

			aaptAddOneFile(apkName + " assets/" + addFilePath, dir);
			aaptRemoveOneFile(apkName + " " + addFiles[0], dir);
			file.deleteOnExit();
			d.deleteOnExit();
			// ==========

			String cmd = getCmdForApksigner(apkName);
			System.out.println("cmd:" + cmd);
			Process process = Runtime.getRuntime().exec(cmd, null, dir);
			proErrInfoShow(process, cmd);
			ArrayList<String> infos = readLins(process.getInputStream());
			showInfo(infos);
			process.destroy();
			
			if(isApkNew)
			new File(newPathChanle).delete();
			
		}

		new File(apkClearPath).deleteOnExit();
		System.out.println(apkClearPath);
		System.out.println("OKOKOMOKOKOKOKO");
	}

	private void addSomeNeedFiles() throws IOException {
		for (String cmd_add : addFiles) {
			aaptAddOneFile(UNSIGN_FILE_NAME_Temp + " " + cmd_add, baseDir);
		}
	}

	private void aaptAddOneFile(String cmd_add, File baseDir)
			throws IOException {
		String cmdadd = "aapt a " + cmd_add;
		System.out.println("cmd:" + cmdadd);
		Process process = Runtime.getRuntime().exec(cmdadd, null, baseDir);
		proErrInfoShow(process, cmdadd);
		ArrayList<String> infos = readLins(process.getInputStream());
		showInfo(infos);
	}

	private void aaptRemoveOneFile(String cmd_add, File baseDir)
			throws IOException {
		String cmdadd = "aapt r " + cmd_add;
		System.out.println("cmd:" + cmdadd);
		Process process = Runtime.getRuntime().exec(cmdadd, null, baseDir);
		proErrInfoShow(process, cmdadd);
		ArrayList<String> infos = readLins(process.getInputStream());
		showInfo(infos);
	}

	private void getOneClearedApk() throws IOException {

		String cmd = "aapt l " + UNSIGN_FILE_NAME_Temp;
		System.out.println("cmd:" + cmd);

		Process process = Runtime.getRuntime().exec(cmd, null, baseDir);
		proErrInfoShow(process, cmd);

		listForDel.clear();
		ArrayList<String> infos = readLins(process.getInputStream());
		showInfo(infos);
		if (infos != null && infos.size() > 0) {
			for (String iii : infos) {
				if (iii != null && !"".equals(iii)) {
					if (iii.startsWith("META-INF")) {
						listForDel.add(iii);
					}
					if (iii.endsWith("kep")) {
						listForDel.add(iii);
					}
				}
			}
		}

		System.out.println("删除");
		
		for (String cmd_dle : listForDel) {
			aaptRemoveOneFile(UNSIGN_FILE_NAME_Temp + " " + cmd_dle, baseDir);
		}
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
		inStream.close();
	}

	private void proErrInfoShow(Process process, String cmd) throws IOException {
		ArrayList<String> infos = readLins(process.getErrorStream());
		if (infos != null && infos.size() > 0) {
			showErrInfo(infos, cmd + "执行出错");
			throw new IOException("");
		}
	}

	private void showErrInfo(ArrayList<String> infos, String errInfo) {

		if (errInfo != null)
			System.err.println(errInfo);
		if (infos != null)
			for (String err : infos) {
				System.err.println(err);
			}
	}
	
	private void showInfo(ArrayList<String> infos) {
		
		if (infos != null)
			for (String err : infos) {
				System.out.println("___"+err);
			}
	}

	private static ArrayList<String> readLins(InputStream inStream)
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

	// static boolean isTest = true;// 生成2个
	static boolean isApkNew=true;// 生成2个

	private String getCmdForApksigner(String apkName) {

		StringBuffer buffer = new StringBuffer();
		buffer.setLength(0);
		buffer.append("jarsigner ");
		buffer.append(SECRET_KEY_FOR_1_DOT_7); // 1.7的jdk要加上这个
//		buffer.append(" -verbose ");
		buffer.append(" -keystore ").append(SECRET_KEY_PATH + SECRET_KEY_NAME);
		// .append( SECRET_KEY_NAME)
		buffer.append(" -storepass ").append(SECRET_KEY_PASS);
		buffer.append(" -keypass ").append(SECRET_KEY_ALIAS_PASS);
		if (isApkNew) {
			buffer.append(" -signedjar ")
					.append(apkName.replace(".apk", "_ed.apk")).append(" ");
		} else {
			buffer.append(" ");
		}

		buffer.append(apkName).append(" ").append(SECRET_KEY_ALIAS).append(" ");

		return buffer.toString();
	}

}
