package com.suyi.cmd;

import java.util.List;

public class StringUtils {

	public static boolean isNull(String className) {
		return (className==null||"".equals(className));
	}

	public static void show(List<String> listend) {
		// TODO Auto-generated method stub
		
		if(listend==null){
			Su.log(" is null");
		}else{
			while(listend.size()>0){
				Su.log(listend.remove(0));
			}
		}
		
	}

}
