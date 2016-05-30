/**
 * 
 */
package com.example.com.suyi.domain;

import com.example.com.suyi.base.PassAble;
import com.example.com.suyi.phone.call.util.Base64Util;
import com.example.com.suyi.phone.call.util.StringUtile;

/**
 * @author "suwg"
 * 2016Äê5ÔÂ30ÈÕ
 */
public class Base64PassAble implements PassAble {

	/* (non-Javadoc)
	 * @see com.example.com.suyi.base.PassAble#pass(java.lang.String)
	 */
	@Override
	public String pass(String info) {
		// TODO Auto-generated method stub
		return StringUtile.getRadmString(9)+ Base64Util.pass(info)+StringUtile.getRadmString(7);
	}

	/* (non-Javadoc)
	 * @see com.example.com.suyi.base.PassAble#decodePass(java.lang.String)
	 */
	@Override
	public String decodePass(String baseString) {
		// TODO Auto-generated method stub
		return  Base64Util.decodePass(baseString);
	}
	
	

}
