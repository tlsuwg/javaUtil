/**
 * 
 */
package com.example.com.suyi.phone.call;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.domain.ContactManger;
import com.example.com.suyi.domain.MsgSetting;
import com.example.com.suyi.domain.PhoneDateDuration;
import com.example.com.suyi.phone.call.app.CallApp;
import com.example.com.suyi.phone.call.app.CallService;
import com.example.com.suyi.phone.call.app.PhoneReceiver;
import com.example.com.suyi.phone.call.util.Su;

/**
 * @author "suwg" 2016年5月17日
 */
public class CallManger {

	public static final int inCall = 1;
	public static final int endCall = 2;

	Context mContext;
	ContactManger mContactManger;
	MsgSetting mMsgSetting;
	TempListener mTempListener;
	boolean isTempErr;
	PhoneReceiver mPhoneReceiver;
	ThreadForPlayVoice mThreadForPlayVoice;
	boolean isGoOnRun = true;//
	boolean isSetCall = true;// 设置

	public CallManger(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	public void onCreate() {
		notifyView("服务启动");
		isSetCall = true;
		try {
			// getAllContacts();
			// getMsgSetting();
			// setTempListener();
			settingCallStatusMonitor();
			initPlater();
			doCall();
			mThreadForPlayVoice.startPlay();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CallApp.getInstance().notifyViewErr(e.getMessage());
		}
	}

	public void onStart(Intent intent, int startId) {
		if(intent!=null)
		notifyView("" + intent.toString());
	}

	public void onDestroy() {
		notifyView("服务结束，注销");
		isSetCall = false;
		if (mPhoneReceiver != null) {
			mPhoneReceiver.trueEnd();
			mContext.unregisterReceiver(mPhoneReceiver);
		}
	}

	private void initPlater() throws Exception {
		// TODO Auto-generated method stub
		mThreadForPlayVoice = new ThreadForPlayVoice(mContext,
				new FaceCallBack<Object>() {

					@Override
					public boolean callback(Object... ts) {
						// TODO Auto-generated method stub
						if (ts == null) {// 播放完毕
							stopCall();
						} else {
							if (ts != null && ts.length >= 1
									&& ts[0] instanceof Exception) {
								// 播放出现问题
								Exception e = (Exception) ts[0];
								CallApp.getInstance().notifyViewErr(
										"播放音频文件出错。技术细节：" + e.getMessage());
								stopCallService();
							}
						}
						return false;
					}

				});
	}

	protected void stopCall() {
		// TODO Auto-generated method stub
		Su.LogW("stopCall");
	}

	protected void stopCallService() {
		// TODO Auto-generated method stub
		CallService.stop(mContext);
	}

	/**
	 * 
	 */
	private void setTempListener() {
		try {
			mTempListener = new TempListener(mContext, new FaceCallBack() {
				@Override
				public boolean callback(Object... ts) {
					// TODO Auto-generated method stub
					if (ts != null && ts.length >= 2) {
						boolean is = (boolean) ts[0];
						int temp = (int) ts[1];
						if (isGoOnRun) {
							if (!is) {
								isGoOnRun = is;
								notifyView("温度到达" + temp + "暂停拨打。");
							}
						} else {
							if (is) {
								isGoOnRun = is;
								notifyView("温度到达" + temp + "继续拨打。");
								doCall();
							}
						}

					}
					return false;
				}
			});
			mTempListener.registerListener();
		} catch (Exception e) {
			// TODO: handle exception
			notifyView("您的手机不存在温度传感器，不能控制温度。");
			isTempErr = true;
		}

	}

	/**
	 * 
	 */
	private void doCall() {

	}

	/**
	 * 
	 */
	private void settingCallStatusMonitor() {
		mPhoneReceiver = new PhoneReceiver(mContext,
				new FaceCallBack<Object>() {
					@Override
					public boolean callback(Object... ts) {
						// TODO Auto-generated method stub
						if (ts != null && ts.length >= 2) {
							int type = (int) ts[0];

							switch (type) {
							case CallManger.inCall:
								mThreadForPlayVoice.startPlay();
								break;
							case CallManger.endCall:
								PhoneDateDuration mPhoneDateDuration = (PhoneDateDuration) ts[1];
							
								mThreadForPlayVoice.stop();
								break;

							default:
								break;
							}

						}
						return false;
					}
				});
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		mIntentFilter.setPriority(Integer.MAX_VALUE);
		mContext.registerReceiver(mPhoneReceiver, mIntentFilter);
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void getAllContacts() throws Exception {

		notifyView("分析联系人");
		mContactManger = new ContactManger(mContext);
		mContactManger.getAllItems();
		notifyView("分析联系人完成");
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void getMsgSetting() throws Exception {
		notifyView("获取短信配置");
		mMsgSetting = new MsgSetting(mContext);
		mMsgSetting.getSetting();
	}

	private void notifyView(String info) {
		// TODO Auto-generated method stub
		CallApp.getInstance().notifyView(info);
	}

}
