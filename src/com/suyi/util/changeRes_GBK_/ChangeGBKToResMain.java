package com.suyi.util.changeRes_GBK_;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChangeGBKToResMain {
	// 必须保证此类（程序入口为GBK）
	// 保证读流为GBK
	// 上面两个条件正常读出GBK文件
	// 然后使用UTF8写出文件
	// 如果本程序不是GBK无法读出GBK文件

	// static String packagePath = "E:\\android_su_app_java\\IMMemoryCache_3";//
	// 必须是\\

	static String packagePath = "E:\\android\\workspace\\PhoneCall";

	static String resPath = "\\res\\raw\\";
	// static String resPath ="\\res\\drawable\\";

	static String newdirstr;

	static HashMap<String, String> mapTrueString = new HashMap<String, String>();
	static HashMap<String, byte[]> mapBytes = new HashMap<String, byte[]>();

	static File newdir;

	public static void main(String[] args) {
		File olddir = new File(packagePath);
		if (!olddir.getAbsolutePath().equals(packagePath)) {
			System.err.println("败家的微软");
			System.err.println("把/改成\\\\");
			System.exit(1);
		}
		newdir = new File(packagePath + "Res");
		newdir.mkdirs();
		newdirstr = newdir.getAbsolutePath();
		changeUTF(olddir);
		todb();
	}

	private static void todb() {
		// TODO Auto-generated method stub

		Iterator<String> it = mapBytes.keySet().iterator();

		ByteArrayOutputStream indexOut = new ByteArrayOutputStream();
		ByteArrayOutputStream bOutput = new ByteArrayOutputStream();

		int size = 0;
		short indexAll = 0;
		while (it.hasNext()) {
			size++;
			String key = it.next();
			byte[] bs = mapBytes.get(key);
			indexAll += bs.length + 2;
			byte[] indexbs = IntByte.shortToByte(indexAll);

			try {
				byte s = (byte) (127 * Math.random());
				bOutput.write(s);
				bOutput.write(bs);
				bOutput.write(s);
				indexOut.write(indexbs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (size == mapBytes.size()) {
				System.out.println("最后一个:" + key + " " + mapTrueString.get(key)
						+ "  " + size + " " + indexAll);
			}
		}

		byte[] allBs = bOutput.toByteArray();
		byte[] indexBs = indexOut.toByteArray();

		System.out.println("数据量:" + size + " bs all len:" + indexAll + " allBs"
				+ allBs.length + " indexBs" + indexBs.length);

		String newpath = newdir.getAbsolutePath() + resPath;

		// showinde(indexBs);

		try {
			for (int y = 1; y <= 7; y++) {
				String filen = newpath + "sua_" + y + ".png";
				if (y == 3) {//
					saveFile(filen, indexBs);
				} else if (y == 5) {// all
					saveFile(filen, allBs);
				} else {// 随机给几个
					byte[] ssss = StringUtile.getRadmString(
							(int) (1024 * Math.random()) + 300).getBytes();
					saveFile(filen, ssss);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private static void showinde(byte[] ss) {
		// TODO Auto-generated method stub
		byte[] bs = new byte[2];

		for (int i = 0; i < ss.length; i += 2) {
			// byte[] bs=ss[i]
			bs[0] = ss[i];
			bs[1] = ss[i + 1];
			short in = IntByte.byteToShort(bs);
			System.out.println("" + in);
		}
	}

	public static void saveFile(String filepath, byte[] data) throws Exception {
		if (data != null) {
			File file = new File(filepath);
			if (file.exists()) {
				file.delete();
			}

			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data, 0, data.length);
			System.out.println("data" + data.length);
			fos.flush();
			fos.close();
		} else {
			System.out.println("null");
		}
	}

	private static void changeUTF(File olddir) {

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
				// if (!isUTF(olddir)) {
				changeFile(olddir);
				return;
				// }
			}

			copyFileToNewDir(olddir);

		}

	}

	static String args[] = new String[] { "layout", "drawable-xxhdpi",
			"drawable-xhdpi", "drawable-mdpi", "drawable-ldpi",
			"drawable-hdpi", "styles", "strings", "xml", "dimens", "assets" };

	static String getRandomName() {

		return args[(int) (args.length * Math.random())];

	}

	private static void changeFile(File olddir) {

		Su_GBKUTF8_FileReader reader = new Su_GBKUTF8_FileReader(olddir,
				isUTF(olddir));
		List<String> list = reader.read();
		list = changeString(list);

		String newfilepath = olddir.getAbsolutePath().replace(packagePath,
				newdirstr);
		File newFile = new File(newfilepath);
		// System.out.println("changeFile  to" + newfilepath);

		try {
			newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Su_UTF_FileWriter writer = new Su_UTF_FileWriter(newFile);

		writer.writeUTF8(list, newFile);

	}

	private static List<String> changeString(List<String> list) {

		ArrayList<String> newList = new ArrayList<String>();
		boolean is = false;

		for (String info : list) {
			info = changeInfo(info);
			newList.add(info);
			if (!is) {
				if (info.trim().startsWith("package")) {
					newList.add("import com.example.com.suyi.phone.call.SUAStringResByteCoder;");
					is = true;
				}
			}
		}
		return newList;
	}

	public static String in = "\"";

	private static String changeInfo(String info) {

		// Su.LogW("call  max : " + max + " current : " + current);

		if (info.trim().startsWith("*") || info.trim().startsWith("\\")
				|| info.trim().startsWith("@") || info.contains("@SUA")) {
			// System.out.println("直接return:" + info);
			return info;
		}

		String old = info;

		if (old.contains(in)) {
			String[] args = old.split(in);
			for (int i = 1; i < args.length; i += 2) {
				String inString = args[i];
				// System.out.println("引号内部：" + inString);
				// try {

				if (inString.length() <=3 ) {
					args[i] = in + inString + in;
					continue;
				}

				byte[] bs = inString.getBytes();
				String key = PassMessageDigestUtil.encodeMD5(bs);

				if (mapTrueString.containsKey(key)) {
					String getValue = mapTrueString.get(key);
					if (!getValue.equals(inString)) {
						System.err.println("这个错误不能忍，该方案" + inString);
					}
				}

				mapTrueString.put(key, inString);
				mapBytes.put(key, bs);

				// SUAStringResByteCoder.getSingleton().get("res___ssss");
				String news = "SUAStringResByteCoder.getSingleton().get(" + in
						+ getRandomName() + "_" + key + in + ")";
				args[i] = news;

				// } catch (UnsupportedEncodingException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}

			StringBuffer sb = new StringBuffer();
			for (String cc : args) {
				sb.append(cc);
			}

			// System.out.println("new " + sb.toString());
			info = sb.toString();

		}

		return info;
	}

	private static void copyFileToNewDir(File olddir) {
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
			//

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
