package com.example.com.suyi.phone.call.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss:SSS");

	public static String getTimeString(long time) {
		// TODO Auto-generated method stub
		return dateFormater.format(new Date(time));
	}

}
