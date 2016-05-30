/**
 * 
 */
package com.example.com.suyi.phone.call.util;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author "suwg" 2016Äê5ÔÂ16ÈÕ
 */
public class StringUtile {

	/**
	 * @param settingVoice
	 * @return
	 */
	public static boolean isEmpty(String settingVoice) {
		// TODO Auto-generated method stub
		return settingVoice == null || settingVoice.length() == 0
				|| "".equals(settingVoice);
	}

	/**
	 * @param list
	 * @return
	 */
	public static String getClearList(LinkedList<String> list) {
		// TODO Auto-generated method stub
		if (list == null)
			return "";
		StringBuffer sb = new StringBuffer();
		while (list.size() > 0) {
			sb.append(list.remove(0)).append("\n");
		}

		return sb.toString();
	}

	/**
	 * @param i
	 * @return
	 */
	public static String getRadmString(int i) {
		// TODO Auto-generated method stub
		String info="";
		for(int k=0;i<i;i++){
			info+=((char)Math.random()*128);
		}
		return info;
	}

	// public static boolean isMobileNO(String mobiles) {
	//
	// Pattern p = Pattern
	// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	//
	// Matcher m = p.matcher(mobiles);
	//
	// // System.out.println(m.matches() + "---");
	//
	// return m.matches();
	//
	// }

}
