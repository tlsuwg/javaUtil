package com.example.com.suyi.phone.call.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.TelephonyManager;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.domain.PhoneDateDuration;
import com.example.com.suyi.phone.call.CallManger;
import com.example.com.suyi.phone.call.ThreadForReadLog;
import com.example.com.suyi.phone.call.activity.StartActivity;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;

public class PhoneReceiver extends BroadcastReceiver implements
		FaceCallBack<Long> {

	public static final SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss:SSS");

	String mOutPhoneNumber, mIncomingNumber;
	Context mContext;
	TelephonyManager tm;
	Handler mHandler = new Handler();
	ThreadForReadLog mThreadForReadLog;
	FaceCallBack<Object> faceCallBack;

	// 全部都是自己获取的
	volatile long callSomeOneStartTimeSelfGet, callSomeOneEndTimeSelfGet,
			callSomeOneItsRingTimeSelfGet, callSomeOneItsCallingTimeSelfGet;
	volatile boolean isComing;
	volatile boolean inStatus;// 就只在整个范围内

	public PhoneReceiver(Context mContext, FaceCallBack<Object> faceCallBack) {
		this.mContext = mContext;
		tm = (TelephonyManager) mContext
				.getSystemService(Service.TELEPHONY_SERVICE);
		this.faceCallBack = faceCallBack;
	}

	void callStatusChange(int i, PhoneDateDuration mDateDuration) {
		faceCallBack.callback(i, mDateDuration);
	}

	@Override
	public boolean callback(Long... ts) {
		// TODO Auto-generated method stub
		if (inStatus && ts != null && ts.length >= 2) {
			long type_times = ts[0];//
			long time = ts[1];

			if (type_times >= ThreadForReadLog.CDMA_REC) {// 通话jietong
				Su.LogW("555555555555对方接通");
				this.callSomeOneItsCallingTimeSelfGet = time;
				callStatusChange(CallManger.inCall, null);
			} else {// 电话号码是可以使用的(对方网络空口判断)，对方电话可以在线

				// A可能是电话卡被拔掉，没有注销，不在服务区，无信号
				// B可以是关机 注销掉服务
				// C也可以是自己的号码，呼叫忙
				// D正在通话

				Su.LogW("555555555555对方响铃");
				this.callSomeOneItsRingTimeSelfGet = time;

			}
		}
		return false;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {// 外拨打
			Su.LogW("11111111111111111111111111111");
			isComing = false;
			if (!inStatus) {
				inStatus = true;
			} else {
				Su.LogW("摘机优先");
			}
			mOutPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			Su.LogW("外拨   摘机 :" + mOutPhoneNumber);
			callSomeOneStartTimeSelfGet = new Date().getTime();
			getCallStatusByLog();
		} else {
			Su.LogW("22222222222222222222");
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// 来了 响铃ing
			{

				mIncomingNumber = intent.getStringExtra("incoming_number");
				if (!inStatus) {
					inStatus = true;
				} else {
					doRefuseIncome(mIncomingNumber);
					// 应该拒绝
				}

				Su.LogW("状态改变：CALL_STATE_RINGING" + mIncomingNumber);
				isComing = true;
			}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 摘机
			{
				String mOutPhoneNumber = intent
						.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				String mIncomingNumber = intent
						.getStringExtra("incoming_number");
				Su.LogW("状态改变：CALL_STATE_OFFHOOK" + "  out:" + mOutPhoneNumber
						+ " in:" + mIncomingNumber);

				if (!inStatus) {
					inStatus = true;
				} else {
					Su.LogW("摘机延后");
				}

				if (isComing || !StringUtile.isEmpty(mIncomingNumber)) {
					Su.LogW("被叫 接通");
				}
			}
				break;
			case TelephonyManager.CALL_STATE_IDLE: {
				Su.LogW("状态改变：CALL_STATE_IDLE   停止");
				if (!inStatus) {
					// inStatus = true;
				} else {
					inStatus = false;
				}

				endReadLog();

				if (!isComing) {
					callSomeOneEndTimeSelfGet = new Date().getTime();
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							PhoneDateDuration mDateDuration = getCallLogState(mOutPhoneNumber);
							callStatusChange(CallManger.endCall, mDateDuration);
							
							callSomeOneStartTimeSelfGet = 0;
							callSomeOneEndTimeSelfGet = 0;
							callSomeOneItsRingTimeSelfGet = 0;
							callSomeOneItsCallingTimeSelfGet = 0;
						}
					}, 1000);
				}
			}
				isComing = false;
				break;
			case 3: {// 傻逼酷派 设置出来个3
				String mOutPhoneNumber = intent
						.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				String mIncomingNumber = intent
						.getStringExtra("incoming_number");
				Su.LogW("状态改变：3" + "  out:" + mOutPhoneNumber + " in:"
						+ mIncomingNumber + "（3）被叫 接通");
			}
				break;
			default:
				Su.LogW("???" + tm.getCallState());
				break;
			}
		}
	}

	// 拒绝
	private void doRefuseIncome(String mIncomingNumber2) {
		// TODO Auto-generated method stub
		doRefuseMsg();
	}

	// 发送决绝之后 短信
	private void doRefuseMsg() {
		// TODO Auto-generated method stub

	}

	private PhoneDateDuration getCallLogState(String number) {
		ContentResolver cr = mContext.getContentResolver();
		final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[] {
				CallLog.Calls.NUMBER, CallLog.Calls.TYPE,
				CallLog.Calls.DURATION, CallLog.Calls.DATE },
				CallLog.Calls.NUMBER + "=? and " + CallLog.Calls.TYPE + "= ?",
				new String[] { number, CallLog.Calls.OUTGOING_TYPE + "" },
				CallLog.Calls.DATE + " desc limit 1");

		// 来电：CallLog.Calls.INCOMING_TYPE （常量值：1）
		// 已拨：CallLog.Calls.OUTGOING_TYPE（常量值：2）
		// 未接：CallLog.Calls.MISSED_TYPE（常量值：3）

		// final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new
		// String[] {
		// CallLog.Calls.NUMBER, CallLog.Calls.TYPE,
		// CallLog.Calls.DURATION, CallLog.Calls.DATE },
		// CallLog.Calls.NUMBER + "=?", new String[] { number},
		// CallLog.Calls.DATE + " desc limit 1");

		while (cursor.moveToNext()) {
			int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
			int dataIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
			int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);

			long lastTime = cursor.getLong(durationIndex);
			long starttimeDB = -1;
			if (dataIndex >= 0) {
				starttimeDB = cursor.getLong(dataIndex);
			}

			int type = -1;
			if (typeIndex >= 0) {
				type = cursor.getInt(typeIndex);
			}

			Su.LogW(getName(type) + " " + " 持续" + lastTime + " DB开始"
					+ (starttimeDB > 0 ? dateFormater.format(starttimeDB) : ""));

			return new PhoneDateDuration(lastTime, starttimeDB).setGetTime(
					callSomeOneStartTimeSelfGet, callSomeOneEndTimeSelfGet,
					callSomeOneItsRingTimeSelfGet,
					callSomeOneItsCallingTimeSelfGet);
			// break;
		}
		return null;
	}

	private String getName(int type) {
		if (type < CallNames.length)
			return CallNames[type];
		return "?" + type;
	}

	public static final String[] CallNames = new String[] { "?0", "来电", "已拨",
			"未接", "语音信箱" };

	private void getCallStatusByLog() {
		if (mThreadForReadLog == null)
			mThreadForReadLog = new ThreadForReadLog(this, mContext, tm);
		mThreadForReadLog.startRead();
	}

	void endReadLog() {
		if (mThreadForReadLog != null)
			mThreadForReadLog.endRead();
	}

	void notifyView(String info) {
		Intent mIntent = new Intent(StartActivity.CallStatus);
		mIntent.putExtra("status", info);
		mContext.sendBroadcast(mIntent);
	}

	public void trueEnd() {
		// TODO Auto-generated method stub
		if (mThreadForReadLog != null) {
			mThreadForReadLog.endRead();
			mThreadForReadLog.trueEnd();
		}
	}

}