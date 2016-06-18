package com.suyi.aapt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApkAddFiles {

	// 需要使用默认环境的方式执行命令
	// aapt没有指定源文件 和存放目录的结构
	// 使用什么样子的结构就放置在什么样子的结构里面
	// 使用到了当前环境变量的方法
	private static final String SECRET_KEY_PATH = "D:/androidAapt/";
	private static final String SECRET_KEY_NAME = "debug.keystore";
	private static final String SECRET_KEY_PASS = "android";
	private static final String SECRET_KEY_ALIAS_PASS = "android";
	private static final String SECRET_KEY_ALIAS = "androiddebugkey";
	private static final String SECRET_KEY_FOR_1_DOT_7 = " -digestalg SHA1 -sigalg MD5withRSA";
	private static final String UNSIGN_FILE_NAME = "a.apk";// 签名 或者未签名
	private static final String UNSIGN_FILE_NAME_Temp = "claered_temp.apk";
	private static final String SIGN_FILE_NAME = "signed/";

	String apkPath;
	String apkClearPath;
	List<String> listForDel = new ArrayList<String>();
	File baseDir;
	List<String> listC = new Vector<String>();
	int ExecutorsThread = 3;

	private static String[] addFiles = new String[] { "assets/Kepler_jar_2016_04_12_11-15-09.kep" };// 每个apk需要添加的内容
	private static String[] chanles = new String[] { "a", "b", "c" };// 渠道名称
	

	public ApkAddFiles() {
		super();
		apkPath = SECRET_KEY_PATH + UNSIGN_FILE_NAME;
		apkClearPath = SECRET_KEY_PATH + UNSIGN_FILE_NAME_Temp;
		baseDir = new File(SECRET_KEY_PATH);
		for (int a='A' ;a<='Z';a++) {
//			for (String cc1 : chanles) {
//				for (String chanle : chanles) {
					listC.add((char)a +"");
//				}
//			}
		}
	}

	public static void main(String args[]) throws IOException,
			InterruptedException {
		ApkAddFiles mApkAddFiles = new ApkAddFiles();
		mApkAddFiles.doThis();
	}

	private void doThis() throws IOException, InterruptedException {
		System.out.println("备份apk");
		aaptUtil.Copy(apkPath, apkClearPath);
		System.out.println("清理apk中签名等信息");
		getOneClearedApk();
	
	
		final File signDir = new File(SECRET_KEY_PATH + SIGN_FILE_NAME);
		final File addDir = new File(SECRET_KEY_PATH + SIGN_FILE_NAME
				+ "assets/");
		if (!addDir.exists()) {
			addDir.mkdir();// 文件夹
		}

		final ExecutorService executor = Executors.newFixedThreadPool(ExecutorsThread);
		for (int i = 0; i < ExecutorsThread; i++) {
			executor.execute(new MakeOneApkTask(signDir, addDir));
		}
		executor.shutdown();

		new Thread(new Runnable() {//监听进程结束
			public void run() {
				while (true) {
					if (executor.isTerminated()) {
						String[] as = addDir.list();
						for (String ss : as) {
							System.out.println("ssssssssssssssssssss" + ss);
						}
						addDir.delete();// 文件删除必须delete
						new File(apkClearPath).deleteOnExit();
						System.out.println("OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK");
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

	

	private void getOneClearedApk() throws IOException, InterruptedException {
		String cmd = " aapt l " + UNSIGN_FILE_NAME_Temp;
		System.out.println("cmd:" + cmd);
		Process process = Runtime.getRuntime().exec(cmd, null, baseDir);
//		process.getOutputStream().close(); 
		listForDel.clear();
		ArrayList<String> infos = aaptUtil.readLins(process.getInputStream());
		aaptUtil.showInfo(infos);
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
		aaptUtil.proErrInfoShow(process, cmd);
		process.waitFor();

		System.out.println("删除");

		for (String cmd_dle : listForDel) {
			aaptUtil.aaptRemoveOneFile(UNSIGN_FILE_NAME_Temp + " " + cmd_dle, baseDir);
		}
		
		System.out.println("全部添加需要files");
		for (String cmd_add : addFiles) {
			aaptUtil.aaptAddOneFile(UNSIGN_FILE_NAME_Temp + " " + cmd_add, baseDir);
		}
	}



	
	// static boolean isTest = true;// 生成2个
	static boolean isApkNew = true;// 生成2个

	private String getCmdForApksigner(String apkName) {
		StringBuffer buffer = new StringBuffer();
		buffer.setLength(0);
		buffer.append("jarsigner ");
		buffer.append(SECRET_KEY_FOR_1_DOT_7); // 1.7的jdk要加上这个
		// buffer.append(" -verbose ");
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

	class MakeOneApkTask implements Runnable {
		File signDir, addDir;
		public MakeOneApkTask(File signDir, File addDir) {
			this.signDir = signDir;
			this.addDir = addDir;
		}

		public void run() {
			String chanle;
			while (listC.size() > 0 && (chanle = listC.remove(0)) != null) {
				try {
					makeOne(chanle);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(2);
				}
			}
		}

		private void makeOne(String chanle) throws IOException,
				InterruptedException {
			String apkName = chanle + ".apk";
			String newPathChanleApk = SECRET_KEY_PATH + SIGN_FILE_NAME
					+ apkName;
			synchronized (apkClearPath) {
				aaptUtil.Copy(apkClearPath, newPathChanleApk);
			}

			// ==========修改渠道文件
			String addFilePath = "Kepler_jar_2016_04_12_11-15-"
					+ (int) (Math.random() * 100) + ".kep";
			File addfile = new File(addDir, addFilePath);
			addfile.createNewFile();// 文件夹存在

			aaptUtil.aaptAddOneFile(apkName + " assets/" + addFilePath, signDir);
			aaptUtil.aaptRemoveOneFile(apkName + " " + addFiles[0], signDir);
			System.out.println("--" + addfile.getAbsolutePath());
			addfile.delete();// 文件夹 VS 0KB
			// ==========
			String cmd = getCmdForApksigner(apkName);
			System.out.println("cmd:" + cmd);
			Process process = Runtime.getRuntime().exec(cmd, null, signDir);
			aaptUtil.proErrInfoShow(process, cmd);
			ArrayList<String> infos = aaptUtil.readLins(process.getInputStream());
			aaptUtil.showInfo(infos);
			process.waitFor();
			if (isApkNew) {
				new File(newPathChanleApk).deleteOnExit();
				System.out.println("--" + newPathChanleApk);
			}
		}
	}

}
