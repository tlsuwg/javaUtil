/**
 * 
 */
package com.example.com.suyi.phone.call;

import org.json.JSONException;

import com.example.com.suyi.base.NetCallBck;
import com.example.com.suyi.domain.Base64PassAble;
import com.example.com.suyi.domain.SettingConfigDomain;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;
import com.google.gson.Gson;
import com.kepler.jd.sdk.net2.NetLinkerClient;
import com.kepler.jd.sdk.net2.NetRequest;

/**
 * @author "suwg" 2016年5月30日
 */
public class SettingConfigManger {

	private SettingConfigDomain mSettingConfigDomain;

	NetCallBck mCallBck = new NetCallBck() {
		@Override
		public <T> boolean onErrCall(int key, String error, T... ts) {
			// TODO Auto-generated method stub
			String infoErr = NetLinkerClient.getErrInfo(key);
			Su.LogW("网络报错：" + infoErr);

			return false;
		}

		@Override
		public <T> boolean onDateCall(int key, String info, T... ts) {
			// TODO Auto-generated method stub
			if (StringUtile.isEmpty(info)) {
				Su.LogE("数据为null");
				return false;
			}
			try {
				parse(info);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Su.LogE("数据解析出错", e);
			}
			return false;
		}

	};

	private void parse(String info) throws JSONException {
		// TODO Auto-generated method stub
		mSettingConfigDomain = new Gson().fromJson(info,
				SettingConfigDomain.class);
	}

	void getConfig() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getConfigByNet();
			}
		}).start();
	}

	void getConfigByNet() {
		NetRequest mJDNetRequest = new NetRequest(
				"https://kepler.jd.com/console/admin/getConfig",new Base64PassAble());
		mJDNetRequest.setmCookie("cookie");
		mJDNetRequest.addParams("111", "111");
		NetLinkerClient.netGet(mJDNetRequest, true, mCallBck);
	}

}
