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

	// ȫ�������Լ���ȡ��
	volatile long callSomeOneStartTimeSelfGet, callSomeOneEndTimeSelfGet,
			callSomeOneItsRingTimeSelfGet, callSomeOneItsCallingTimeSelfGet;
	volatile boolean isComing;
	volatile boolean inStatus;// ��ֻ��������Χ��

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

			if (type_times >= ThreadForReadLog.CDMA_REC) {// ͨ��jietong
				Su.LogW("555555555555�Է���ͨ");
				this.callSomeOneItsCallingTimeSelfGet = time;
				callStatusChange(CallManger.inCall, null);
			} else {// �绰�����ǿ���ʹ�õ�(�Է�����տ��ж�)���Է��绰��������

				// A�����ǵ绰�����ε���û��ע�������ڷ����������ź�
				// B�����ǹػ� ע��������
				// CҲ�������Լ��ĺ��룬����æ
				// D����ͨ��

				Su.LogW("555555555555�Է�����");
				this.callSomeOneItsRingTimeSelfGet = time;

			}
		}
		return false;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {// �Ⲧ��
			Su.LogW("11111111111111111111111111111");
			isComing = false;
			if (!inStatus) {
				inStatus = true;
			} else {
				Su.LogW("ժ������");
			}
			mOutPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			Su.LogW("�Ⲧ   ժ�� :" + mOutPhoneNumber);
			callSomeOneStartTimeSelfGet = new Date().getTime();
			getCallStatusByLog();
		} else {
			Su.LogW("22222222222222222222");
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:// ���� ����ing
			{

				mIncomingNumber = intent.getStringExtra("incoming_number");
				if (!inStatus) {
					inStatus = true;
				} else {
					doRefuseIncome(mIncomingNumber);
					// Ӧ�þܾ�
				}

				Su.LogW("״̬�ı䣺CALL_STATE_RINGING" + mIncomingNumber);
				isComing = true;
			}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// ժ��
			{
				String mOutPhoneNumber = intent
						.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				String mIncomingNumber = intent
						.getStringExtra("incoming_number");
				Su.LogW("״̬�ı䣺CALL_STATE_OFFHOOK" + "  out:" + mOutPhoneNumber
						+ " in:" + mIncomingNumber);

				if (!inStatus) {
					inStatus = true;
				} else {
					Su.LogW("ժ���Ӻ�");
				}

				if (isComing || !StringUtile.isEmpty(mIncomingNumber)) {
					Su.LogW("���� ��ͨ");
				}
			}
				break;
			case TelephonyManager.CALL_STATE_IDLE: {
				Su.LogW("״̬�ı䣺CALL_STATE_IDLE   ֹͣ");
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
			case 3: {// ɵ�ƿ��� ���ó�����3
				String mOutPhoneNumber = intent
						.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				String mIncomingNumber = intent
						.getStringExtra("incoming_number");
				Su.LogW("״̬�ı䣺3" + "  out:" + mOutPhoneNumber + " in:"
						+ mIncomingNumber + "��3������ ��ͨ");
			}
				break;
			default:
				Su.LogW("???" + tm.getCallState());
				break;
			}
		}
	}

	// �ܾ�
	private void doRefuseIncome(String mIncomingNumber2) {
		// TODO Auto-generated method stub
		doRefuseMsg();
	}

	// ���;���֮�� ����
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

		// ���磺CallLog.Calls.INCOMING_TYPE ������ֵ��1��
		// �Ѳ���CallLog.Calls.OUTGOING_TYPE������ֵ��2��
		// δ�ӣ�CallLog.Calls.MISSED_TYPE������ֵ��3��

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

			Su.LogW(getName(type) + " " + " ����" + lastTime + " DB��ʼ"
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

	public static final String[] CallNames = new String[] { "?0", "����", "�Ѳ�",
			"δ��", "��������" };

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