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
 * @author "suwg" 2016��5��17��
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
	boolean isSetCall = true;// ����

	public CallManger(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	public void onCreate() {
		notifyView("��������");
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
		notifyView("���������ע��");
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
						if (ts == null) {// �������
							stopCall();
						} else {
							if (ts != null && ts.length >= 1
									&& ts[0] instanceof Exception) {
								// ���ų�������
								Exception e = (Exception) ts[0];
								CallApp.getInstance().notifyViewErr(
										"������Ƶ�ļ���������ϸ�ڣ�" + e.getMessage());
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
								notifyView("�¶ȵ���" + temp + "��ͣ����");
							}
						} else {
							if (is) {
								isGoOnRun = is;
								notifyView("�¶ȵ���" + temp + "��������");
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
			notifyView("�����ֻ��������¶ȴ����������ܿ����¶ȡ�");
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

		notifyView("������ϵ��");
		mContactManger = new ContactManger(mContext);
		mContactManger.getAllItems();
		notifyView("������ϵ�����");
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void getMsgSetting() throws Exception {
		notifyView("��ȡ��������");
		mMsgSetting = new MsgSetting(mContext);
		mMsgSetting.getSetting();
	}

	private void notifyView(String info) {
		// TODO Auto-generated method stub
		CallApp.getInstance().notifyView(info);
	}

}
