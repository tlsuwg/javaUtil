package com.suyi.android.util.makeSecretFile;

public class StringInt {

	// 解密
	public static String getStringByStringarray(String[] args)
			throws SuAPKException {

		if (args == null || args.length == 0) {
			throw new SuAPKException("转化无填充数据");
		}
		String base = "";
		for (String ss : args) {
			if (ss == null || "".equals(ss)) {
				throw new SuAPKException("转化出错");
			}

			int k = Integer.parseInt(ss);

			int kk = k + 3;

			base += (char) kk;

		}

		return base;

	}

	
//	加密
//	public static String[] getStringArrayByString(String args)
//			throws SuAPKException {
//
//		if (args == null || args.length() == 0) {
//			throw new SuAPKException("转化无填充数据");
//		}
//
//		String base[] = new String[args.length()];
//
//		for (int i = 0; i < args.length(); i++) {
//
//			char ss = args.charAt(i);
//
//			int k = ss;
//			base[i] = k - 3 + "";
//
//		}
//
//		return base;
//
//	}

//	加密
	public static String getStringArray_StringByString(String args)
			throws SuAPKException {

		if (args == null || args.length() == 0) {
			throw new SuAPKException("转化无填充数据");
		}
		String Sp = (char) 0 + "";
		String base = Sp;
		for (int i = 0; i < args.length(); i++) {
			char ss = args.charAt(i);
			int k = ss;
			base += (k - 3) + Sp;
		}
		return base;
	}

}
