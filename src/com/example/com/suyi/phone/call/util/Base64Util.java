/**
 * 
 */
package com.example.com.suyi.phone.call.util;

import android.util.Base64;

/**
 * @author "suwg" 2016Äê5ÔÂ30ÈÕ
 */
public class Base64Util {

	public static String pass(String info) {
		if (StringUtile.isEmpty(info)) {
			info = "";
		}
		return new String(Base64.encode(info.getBytes(), Base64.DEFAULT));
	}

	public static String decodePass(String baseString) {
		if (StringUtile.isEmpty(baseString)) {
			baseString = "";
		}
		return new String(Base64.decode(baseString, Base64.DEFAULT));
	}
}
