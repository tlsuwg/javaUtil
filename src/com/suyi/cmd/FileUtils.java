package com.suyi.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by jd-yp on 2015/12/30.
 */
public class FileUtils {

	/**
	 * 计算一个文件的md5
	 */
	public static String getMd5ByFile(File file) {
		FileInputStream in=null;
		try {
			 in = new FileInputStream(file);
			MessageDigest digester = MessageDigest.getInstance("MD5"); // TODO

			byte[] bytes = new byte[8192];
			int byteCount;
			while ((byteCount = in.read(bytes)) > 0) {
				digester.update(bytes, 0, byteCount);
			}
			byte[] digest = digester.digest();
			BigInteger bi = new BigInteger(1, digest);
			return String.format("%032x", bi);
		} catch (Exception e) {
			return "";
		} catch (Error e) {
			return "";
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 计算一段字符串的md5
	 */

	public static String getMd5(String source) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(source.getBytes());
			BigInteger bi = new BigInteger(1, md5.digest());
			return String.format("%032x", bi);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取外部存储目录，一般是/sdcard/ 如果不存在，就返回/data/ 因为现在静态资源没有办法完全保证，所以所有的SDK共享一份静态的功能关闭
	 * suwg
	 */





	/**
	 * 解压zip文件
	 */
//	public static void unzipFile(String filePath, String unzipPath) {
//		try {
//			ZipFile zipFile = new ZipFile(filePath);
//			for (Enumeration entry = zipFile.entries(); entry.hasMoreElements();) {
//				ZipEntry zipEntry = (ZipEntry) entry.nextElement();
//				if (zipEntry.isDirectory()) {
//					// 打开注释查看解压了哪些文件
//					continue;
//				}
//				// 有文件的子目录
//				if (zipEntry.getSize() > 0) {
//					File file = FileUtils.getFileByPath(unzipPath + "/"
//							+ zipEntry.getName(), false);
//
//					OutputStream os = new BufferedOutputStream(
//							new FileOutputStream(file));
//					InputStream is = zipFile.getInputStream(zipEntry);
//					byte[] buffer = new byte[4096];
//					int len = 0;
//					while ((len = is.read(buffer)) >= 0) {
//						os.write(buffer, 0, len);
//					}
//					is.close();
//					os.flush();
//					os.close();
//				}
//				// 空目录
//				else {
//					FileUtils.getFileByPath(
//							unzipPath + "/" + zipEntry.getName(), true);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// /**
	// * 从assets中解压module.zip
	// */
	// public static void unzipAssetModule(Context context) {
	// try {
	// if ((context == null) || (context.getResources() == null)
	// || (context.getResources().getAssets() == null)) {
	// return;
	// }
	//
	// InputStream is = context.getResources().getAssets()
	// .open("module.zip");
	// ZipInputStream in = new ZipInputStream(is);
	// ZipEntry entry = in.getNextEntry();
	// while (entry != null) {
	// if (entry.isDirectory()) {
	// File file = new File(FileUtils.getAppRoot(),
	// entry.getName());
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	// } else {
	// File file = new File(FileUtils.getAppRoot(),
	// entry.getName());
	// OutputStream os = new BufferedOutputStream(
	// new FileOutputStream(file));
	// byte[] buffer = new byte[1000000];
	// int len = 0;
	// while ((len = in.read(buffer)) >= 0) {
	// os.write(buffer, 0, len);
	// }
	//
	// os.flush();
	// os.close();
	// }
	// entry = in.getNextEntry();
	// }
	// in.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 拷贝目录，从src到dst
	 */
	public static void copyDir(String src, String dst) {
		try {
			File fileSrc = new File(src);
			if (!fileSrc.exists()) {
				return;
			}

			File[] filelist = fileSrc.listFiles();
			File fileDst = new File(dst);
			if (!fileDst.exists()) {
				fileDst.mkdirs();
			}

			for (File f : filelist) {
				if (f.isDirectory()) {
					copyDir(f.getPath() + "/", dst + f.getName() + "/");
				} else {
					copyFile(f.getPath(), dst + f.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝文件，从src到dst
	 */
	public static boolean copyFile(String src, String to) {

		Su.log("from"+src);
		Su.log("to"+to);

		FileInputStream in = null;
		FileOutputStream out=null;
		try {
			File fto=new File(to);
				fto.mkdirs();
			fto.delete();
//			fto.deleteOnExit();
			fto.createNewFile();

			 in = new FileInputStream(src);
			 out = new FileOutputStream(fto);
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = in.read(b)) > 0) {
				out.write(b, 0, len);
			}

			System.out.println("完成to："+to);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(in!=null)
					in.close();
				if(out!=null)
					out.close();
			}catch (Exception e){

			}

		}
	}

	public static void safetyDeleteFile(String src) {
		File oldFile = new File(src);
		safetyDeleteFile(oldFile);
	}

	public static void safetyDeleteFile(File file) {
		if (file == null)
			return;
		try {

			if (file != null && file.exists() && file.isFile()) {
				file.delete();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 只会删除文件，留下空文件夹
	public static void safetyDeleteDir(File dir) {

		if (dir == null)
			return;
		// 判断是文件还是目录
		if (dir.isFile() || dir.listFiles().length == 0) {// 若目录下没有文件则直接删除
			dir.delete();
		} else {// 若有则把文件放进数组，并判断是否有下级目录
			File[] delFile = dir.listFiles();
			int i = delFile.length;
			for (int j = 0; j < i; j++) {
				safetyDeleteDir(delFile[j]);// 递归调用del方法并取得子目录路径
				delFile[j].delete();
			}
		}

	}

	public static void safetyDeleteDir(String dirSrc) {
		File oldFile = new File(dirSrc);
		safetyDeleteDir(oldFile);
	}

}
