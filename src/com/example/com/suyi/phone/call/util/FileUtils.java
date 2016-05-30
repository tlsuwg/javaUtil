package com.example.com.suyi.phone.call.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.os.Environment;

import com.example.com.suyi.phone.call.KeplerContants;

/**
 * Created by jd-yp on 2015/12/30.
 */
public class FileUtils {

	/**
	 * 计算一个文件的md5
	 */
	public static String getMd5ByFile(File file) {
		try {
			FileInputStream in = new FileInputStream(file);
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
	 * 获取外部存储目录，一般是/sdcard/ 如果不存在，就返回/data/
	 * 因为现在静态资源没有办法完全保证，所以所有的SDK共享一份静态的功能关闭 suwg
	 */
	@Deprecated  
	public static File getDataRoot() {
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
					|| (Environment.getExternalStorageDirectory().exists() && Environment
							.getExternalStorageDirectory().canWrite())) {
				return Environment.getExternalStorageDirectory();
			} else {
				return Environment.getDataDirectory();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public static File getAppDataRoot(Context con) {
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
					|| (Environment.getExternalStorageDirectory().exists() && Environment
							.getExternalStorageDirectory().canWrite())) {
				if(con!=null){
					return con.getExternalCacheDir();
				}
				return Environment.getExternalStorageDirectory();
			} else {
				if(con!=null){
					return con.getCacheDir();
				}
				return Environment.getDataDirectory();
			}
//			
		} catch (Exception e) {
			return null;
		}
	}
	

	public static File getAppRoot(Context con) {
		try {
			File root = new File(getAppDataRoot(con), KeplerContants.MODULE_DIR_ROOT);
			if (!root.exists()) {
				root.mkdirs();
			}

			// 创建 .nomedia 文件，防止app目录下的图片被加入到系统相册
			File nomedia = new File(root, ".nomedia");
			if (!nomedia.exists()) {
				nomedia.createNewFile();
			}

			return root;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取H5离线模块存储的目录
	 */
	public static File getModuleRoot(Context con) {
		try {
			File root = new File(getAppRoot(con), "module");
			if (!root.exists()) {
				root.mkdirs();
			}
			return root;
		} catch (Exception e) {
			return null;
		}
	}

	private static File getFileByPath(Context con,String path, boolean isDir) {
		File fileModuleRoot = getModuleRoot(con);
		File file = new File(fileModuleRoot, path);
		if (!file.exists()) {
			if (isDir) {
				file.mkdirs();
			} else {
				file.getParentFile().mkdirs();
				file = new File(fileModuleRoot, path);
			}
		}
		return file;
	}

	/**
	 * 根据url获取file (H5离线模块服务专用)
	 * 如这样的：http://wqs.jd.com/data/h5module/app_item/app_item.1.41.zip
	 * 这样的：http://wqs.jd.com/data/h5module/app_item/app_item.1.41.patch.zip
	 * 这些文件会被下载到/sdcard/com.jd.wxsq.app/module/目录下
	 */
	public static File getModuleFile(Context con,String url) {
		String name = url.substring(url.lastIndexOf('/') + 1);
		return new File(getModuleRoot(con), name);
	}

	/**
	 * 解压zip文件
	 */
	public static void unzipFile(Context con,String filePath, String unzipPath) {
		try {
			ZipFile zipFile = new ZipFile(filePath);
			for (Enumeration entry = zipFile.entries(); entry.hasMoreElements();) {
				ZipEntry zipEntry = (ZipEntry) entry.nextElement();
				if (zipEntry.isDirectory()) {
					// 打开注释查看解压了哪些文件
					continue;
				}
				// 有文件的子目录
				if (zipEntry.getSize() > 0) {
					File file = FileUtils.getFileByPath(con,unzipPath + "/"
							+ zipEntry.getName(), false);
					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(file));
					InputStream is = zipFile.getInputStream(zipEntry);
					byte[] buffer = new byte[4096];
					int len = 0;
					while ((len = is.read(buffer)) >= 0) {
						os.write(buffer, 0, len);
					}
					is.close();
					os.flush();
					os.close();
				}
				// 空目录
				else {
					FileUtils.getFileByPath(con,
							unzipPath + "/" + zipEntry.getName(), true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从assets中解压module.zip
	 */
	public static void unzipAssetModule(Context context) {
		try {
			if ((context == null) || (context.getResources() == null)
					|| (context.getResources().getAssets() == null)) {
				return;
			}

			InputStream is = context.getResources().getAssets()
					.open("module.zip");
			ZipInputStream in = new ZipInputStream(is);
			ZipEntry entry = in.getNextEntry();
			while (entry != null) {
				if (entry.isDirectory()) {
					File file = new File(FileUtils.getAppRoot(context),
							entry.getName());
					if (!file.exists()) {
						file.mkdirs();
					}
				} else {
					File file = new File(FileUtils.getAppRoot(context),
							entry.getName());
					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(file));
					byte[] buffer = new byte[1000000];
					int len = 0;
					while ((len = in.read(buffer)) >= 0) {
						os.write(buffer, 0, len);
					}

					os.flush();
					os.close();
				}
				entry = in.getNextEntry();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public static void copyFile(String src, String dst) {
		try {
			FileInputStream in = new FileInputStream(src);
			FileOutputStream out = new FileOutputStream(dst);
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = in.read(b)) > 0) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void safetyDeleteFile(String src) {
		File oldFile = new File(src);
		safetyDeleteFile(oldFile);
	}

	public static void safetyDeleteFile(File file) {
		if (file != null && file.exists() && file.isFile()) {
			file.delete();
		}
	}

	//只会删除文件，留下空文件夹
	public static void safetyDeleteDir(File dir) {
			// 判断是文件还是目录
			if (dir.isFile() ||dir.listFiles().length == 0) {// 若目录下没有文件则直接删除
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
