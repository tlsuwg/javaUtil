/**
 * 
 */
package com.kepler.jd.sdk.net2;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.com.suyi.base.PassAble;

public class JDNetRequest {

	private List<BasicNameValuePair> params;
	private List<BasicNameValuePair> headParams;
	private String mCookie;
	private String URL;
	private boolean isHttps;
	private PassAble mPassAble;//º”√‹

	/**
	 * @param httpHost
	 */
	@Deprecated
	public JDNetRequest(String httpHost) {
		// TODO Auto-generated constructor stub
		this.URL = httpHost;
		getHeaderP().add(new BasicNameValuePair("1", "1"));
	}
	
	public JDNetRequest(String httpHost,PassAble mPassAble) {
		// TODO Auto-generated constructor stub
		this.URL = httpHost;
		this.mPassAble=mPassAble;
		getHeaderP().add(new BasicNameValuePair("1", new Date().getTime()+""));
	}
	
	
	

	private List<BasicNameValuePair> getP() {
		if (params == null)
			params = new LinkedList<BasicNameValuePair>();
		return params;
	}

	/**
	 * @return
	 */
	private List<BasicNameValuePair> getHeaderP() {
		if (headParams == null)
			headParams = new LinkedList<BasicNameValuePair>();
		return headParams;
	}

	public void addParams(String key, String val) {
		if(mPassAble!=null){
			val=mPassAble.pass(val);
		}
		getP().add(new BasicNameValuePair(key, val));
	}

	public void addHeadParams(String key, String val) {
		if(mPassAble!=null){
			val=mPassAble.pass(val);
		}
		getHeaderP().add(new BasicNameValuePair(key, val));
	}

	/**
	 * @return the params
	 */
	public List<BasicNameValuePair> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(List<BasicNameValuePair> params) {
		this.params = params;
	}

	/**
	 * @return the mCookie
	 */
	public String getmCookie() {
		return mCookie;
	}

	/**
	 * @param mCookie
	 *            the mCookie to set
	 */
	public void setmCookie(String mCookie) {
		this.mCookie = mCookie;
	}

	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL
	 *            the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
		if (URL != null && URL.startsWith("https:")) {
			isHttps = true;
		}
	}

	/**
	 * @return the isHttps
	 */
	public boolean isHttps() {
		return isHttps;
	}

	/**
	 * @param isHttps
	 *            the isHttps to set
	 */
	public void setHttps(boolean isHttps) {
		this.isHttps = isHttps;
	}

}
