package com.suyi.util.deleteInSrc.info;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DeleteProkactInfo {

	// static String packagePath =
	// "E:\\\android_su_app_java\\IMMemoryCache_3";// ������\\

	static String packagePath = "E:\\android_link\\pdouADNew3.5_ss_new";// ������\\

	static String newdirstr;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File olddir = new File(packagePath);

		if (!olddir.getAbsolutePath().equals(packagePath)) {
			System.err.println("�ܼҵ�΢��");
			System.err.println("��/�ĳ�\\");
			System.exit(1);
		}

		File newdir = new File(packagePath + "_" + "new");

		if (newdir.exists())
			newdir.deleteOnExit();

		newdir.mkdir();

		newdirstr = newdir.getAbsolutePath();

		deleteInfo(olddir);

	}

	private static void deleteInfo(File olddir) {
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
					deleteInfo(f);
				}
			}
		} else if (olddir.isFile()
				&& (!olddir.getAbsolutePath().endsWith(".class"))) {

			if (olddir.getAbsolutePath().endsWith(".java")) {
//				if (!isUTF(olddir)) {
					clearInfo(olddir);
					return;
//				}
			}

			copyFileToNewDir(olddir);

		}

	}

	private static void clearInfo(File olddir) {
		// TODO Auto-generated method stub

		Su_UTF_FileReader reader = new Su_UTF_FileReader(olddir);

		List<String> list = reader.read();

		List<String> listnew = ClearList(list);

		String newfilepath = olddir.getAbsolutePath().replace(packagePath,
				newdirstr);

		File newFile = new File(newfilepath);

		// System.out.println("delete info  to" + newfilepath);

		try {
			newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Su_UTF_FileWriter writer = new Su_UTF_FileWriter(newFile);

		writer.writeUTF8(listnew, newFile);

	}

	private static List<String> ClearList(List<String> list) {
		// TODO Auto-generated method stub

		if (list == null)
			return null;

		List<String> listnew = new ArrayList<String>();

		Iterator<String> it = list.iterator();
		boolean isneedDelsete=false;
		while (it.hasNext()) {
			String info = it.next();
			String changindo = null;

			if (info.contains("//")&&!info.contains("http:")){

				int index = info.indexOf("//");
				changindo = info.substring(0, index);

				System.out.println(info + "  ");
				System.err.println(changindo + "  ");

			} else {
				changindo = info;
			}
			
			

			if (changindo.contains("Su.log")||isneedDelsete) {
				isneedDelsete=true;
				if(changindo.contains(";")){
					isneedDelsete=false;
				}
				
				changindo = null;
				
			} else {
				changindo = changindo;
			}

			
			if (changindo != null && !"".equals(changindo)) {
				listnew.add(changindo);
			}
		}

		return listnew;
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

	
}
