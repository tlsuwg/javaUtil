/**
 * 
 */
package com.kepler.jd.sdk.net2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.com.suyi.base.NetCallBck;
import com.example.com.suyi.phone.call.util.StringUtile;


public class NetLinkerClient {

	public static final int NetLinker_Err_UnsupportedEncodingException = -1000;
	public static final int NetLinker_Err_ClientProtocolException = -1001;
	public static final int NetLinker_Err_IOException = -1002;
	public static final int NetLinker_Err_Not_200 = -1003;
	public static final int NetLinker_OK = 1000;

	static Map<Integer, String> infoMap = new HashMap<Integer, String>();
	static {
		infoMap.put(NetLinker_Err_UnsupportedEncodingException,
				"NetLinker_Err_UnsupportedEncodingException");
		infoMap.put(NetLinker_Err_ClientProtocolException,
				"NetLinker_Err_ClientProtocolException");
		infoMap.put(NetLinker_Err_IOException, "NetLinker_Err_IOException");
		infoMap.put(NetLinker_Err_Not_200, "NetLinker_Err_Not_200");
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getErrInfo(int key) {
		// TODO Auto-generated method stub
		
		
		return infoMap.get(key);
	}

	public static void netGet(JDNetRequest mJDNetRequest, boolean isPost,
			NetCallBck mBack) {
		if (mBack == null)
			return;

		try {
			List<BasicNameValuePair> params = mJDNetRequest.getParams();
			HttpUriRequest httpRequest = null;
			if (isPost) {
				HttpPost mHttpPost = new HttpPost(mJDNetRequest.getURL());
				if (params != null) {
					mHttpPost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
				}
				httpRequest = mHttpPost;
			} else {
				String param = null;
				if (params != null) {
					param = URLEncodedUtils.format(params, "UTF-8");
				}
				HttpGet getMethod = new HttpGet(mJDNetRequest.getURL()
						+ ((param == null || "".equals(param)) ? ""
								: ("?" + param)));
				httpRequest = getMethod;
			}
			if (mJDNetRequest.getmCookie() != null) {
				httpRequest.addHeader("cookie", mJDNetRequest.getmCookie());
			}

			HttpClient mClient = getHttpClient(mJDNetRequest.isHttps());
			HttpResponse httpResponse = mClient.execute(httpRequest);

			int resCode = httpResponse.getStatusLine().getStatusCode();
			if (resCode == 200) {
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				
				mBack.onDateCall(NetLinker_OK, strResult, httpResponse);
			} else {
				String strResult = httpResponse.getStatusLine().toString();
				mBack.onErrCall(NetLinker_Err_Not_200, strResult);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			mBack.onErrCall(NetLinker_Err_UnsupportedEncodingException,
					e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			mBack.onErrCall(NetLinker_Err_ClientProtocolException,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			mBack.onErrCall(NetLinker_Err_IOException, e.getMessage());
		}
	}

	private static HttpClient getHttpClient(boolean isHttps) {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpClientParams.setRedirecting(httpParams, true);
		// String userAgent =
		// "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		// HttpProtocolParams.setUserAgent(httpParams, userAgent);

		if (isHttps) {
			try {
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), 80));

				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);
				SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); // 鍏佽鎵�鏈変富鏈虹殑楠岃瘉
				registry.register(new Scheme("https",
						new SSLSocketFactory(null), 443));
				ClientConnectionManager ccm = new ThreadSafeClientConnManager(
						httpParams, registry);
				return new DefaultHttpClient(ccm, httpParams);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new DefaultHttpClient(httpParams);
	}

	public static Map<String, String> getCookies(HttpResponse httpResponse) {
		Header[] headers = httpResponse.getHeaders("Set-Cookie");
		String headerstr = headers.toString();
		if (headers == null)
			return null;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < headers.length; i++) {
			String cookie = headers[i].getValue();
			String[] cookievalues = cookie.split(";");
			for (int j = 0; j < cookievalues.length; j++) {
				String[] keyPair = cookievalues[j].split("=");
				String key = keyPair[0].trim();
				String value = keyPair.length > 1 ? keyPair[1].trim() : "";
				map.put(key, value);
			}
		}

		return map;
	}

	private static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {

				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

}
