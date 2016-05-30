/**
 * 
 */
package com.kepler.jd.sdk.net2;

import com.example.com.suyi.base.PassAble;
import com.example.com.suyi.phone.call.app.CallApp;

/**
 * @author "suwg"
 * 2016Äê5ÔÂ30ÈÕ
 */
public class NetRequest extends JDNetRequest {

	/**
	 * @param httpHost
	 */
	public NetRequest(String httpHost) {
		super(httpHost);
		// TODO Auto-generated constructor stub
		addHeadParams("baseInfo", CallApp.getInstance().phoneInfo);
	}

	/**
	 * @param httpHost
	 * @param mPassAble
	 */
	public NetRequest(String httpHost, PassAble mPassAble) {
		super(httpHost, mPassAble);
		// TODO Auto-generated constructor stub
		addHeadParams("baseInfo", CallApp.getInstance().phoneInfo);
	}
	
	

}
