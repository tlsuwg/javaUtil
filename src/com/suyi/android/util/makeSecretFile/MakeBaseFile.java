package com.suyi.android.util.makeSecretFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//这个是加密工程
public class MakeBaseFile {

	static String BasePackagePath = "E://android_su_app//CommApp";// 基础程序package
																	// 路径
	static String BasePackagePathAPK = "C://Users//suwg//Desktop//getapks//";// 输出的地址
	static String packageChangeSetting = "C://Users//suwg//Desktop//getapks//";// 输出的地址
	static String antPathS = "E://apache-ant-1.8.2//bin//";//
	static String lead1 = "ant.bat -buildfile ";
	static String lead2 = "//suyibuild.xml release";
	static String needMakerNo = "2";// 这个是对应PC端 电脑核心数的
	
	

	// static String BasePackagePath =
	// "E://java//Android//android-sdk//samples//android-17//CommApp";//
	// 基础程序package 路径
	// static String BasePackagePathAPK =
	// "C://Users//pdou_85//Desktop//getapks//";// 输出的地址
	// static String packageChangeSetting =
	// "C://Users//pdou_85//Desktop//getapks//";// 输出的地址
	// static String antPathS =
	// "E://java//eclipse//plugins//org.apache.ant_1.8.4.v201303080030//bin//";//
	// static String lead1 = "ant.bat -buildfile ";
	// static String lead2 = "//suyibuild.xml release";
	// static String needMakerNo = "2";//这个是对应PC端 电脑核心数的

//	static String reFileNames = "E://java//Android//android-sdk//samples//android-17//hwr//re.sre";// this
																									// is
																									// hongwai
	
	static String reFileNames = "D://android_base\\adt-bundle-windows-x86_64-20131030\\sdk\\platforms\\android-19\\templates//re.wav";// this
	

	static String Path = "E:/android_su_app_java/MemoryCacheForCallforAllLocclient_apk3_UTF/dat/frec.db";// 这个是文件生成的

	public static void main(String args[]) throws SuAPKException {

		File f = new File(Path);

		if (f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List list = new ArrayList();
		String ss = StringInt.getStringArray_StringByString(BasePackagePath);
		list.add("ac;;;" + 0 + ss);
		ss = StringInt.getStringArray_StringByString(BasePackagePathAPK);
		list.add("ac;;;" + 1 + ss);
		ss = StringInt.getStringArray_StringByString(packageChangeSetting);
		list.add("ac;;;" + 2 + ss);
		ss = StringInt.getStringArray_StringByString(antPathS);
		list.add("ac;;;" + 3 + ss);
		ss = StringInt.getStringArray_StringByString(lead1);
		list.add("ac;;;" + 4 + ss);
		ss = StringInt.getStringArray_StringByString(lead2);
		list.add("ac;;;" + 5 + ss);
		ss = StringInt.getStringArray_StringByString(needMakerNo);
		list.add("ac;;;" + 6 + ss);
		ss = StringInt.getStringArray_StringByString(reFileNames);
		list.add("ac;;;" + 7 + ss);

		Su_UTF_FileWriter mSu_UTF_FileWriter = new Su_UTF_FileWriter(f);

		mSu_UTF_FileWriter.writeUTF8(list, f);

	}

}
