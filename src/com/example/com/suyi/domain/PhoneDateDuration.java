package com.example.com.suyi.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.com.suyi.phone.call.app.PhoneReceiver;
import com.example.com.suyi.phone.call.util.DateUtil;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;

public class PhoneDateDuration {

	static final int RingToGetTime = 1000;// ��ôҲ��Ҫ1����ܽ�����

	public static final int Call_UnKnoe = 1;// ͨ��
	public static final int OutNoRing = 2;// �����ж�û�гɹ�
	public static final int OutOnlyHas = 3;// ֻ������
	public static final int OutInCall = 4;// ͨ��

	static String CallStatsName[] = new String[] { "�������״̬��", "�����ж�û�гɹ�",
			"ֻ������", "ͨ��" };

	// �绰�����ǿ���ʹ�õ�(�Է�����տ��ж�)���Է��绰��������

	// A�����ǵ绰�����ε���û��ע�������ڷ����������ź�
	// B�����ǹػ� ע��������
	// CҲ�������Լ��ĺ��룬����æ
	// D����ͨ��

	// ����ʱ�� ����ֻ�Ǻ��� ��ʱ����0��������֮��ĵȴ��Է���ͨ��ʱ�䣬Ҳ���������ͨ��ʱ��

	public long lastTime;
	public long startTimeDB;

	/**
	 * @param lastTime2
	 * @param time2
	 */
	public PhoneDateDuration(long lastTime, long time) {
		// TODO Auto-generated constructor stub
		this.lastTime = lastTime;
		this.startTimeDB = time;

	}

	public int getType() {
		if (lastTime == 0)
			return OutNoRing;
		if (callSomeOneItsCallingTimeSelfGet > 0) {
			return OutInCall;
		}
		if (callSomeOneItsRingTimeSelfGet > 0) {
			return OutOnlyHas;
		}

		return Call_UnKnoe;
	}

	public String getCallTypeName(int type) {
		return CallStatsName[type - 1];
	}

	public String getInfo(){
		String in;
		if(callSomeOneItsCallingTimeSelfGet>0){
			in="\n����:"+(callSomeOneItsRingTimeSelfGet-callSomeOneStartTimeSelfGet)
				+"\n���壺"+(callSomeOneItsCallingTimeSelfGet-callSomeOneItsRingTimeSelfGet)
				+"\nͨ��"+(callSomeOneEndTimeSelfGet-callSomeOneItsCallingTimeSelfGet);
			
		}else{
			if(callSomeOneItsRingTimeSelfGet>0){
				in="\n����:"+(callSomeOneItsRingTimeSelfGet-callSomeOneStartTimeSelfGet)
						+"\n���壺"+(callSomeOneEndTimeSelfGet-callSomeOneItsRingTimeSelfGet);
			}else{
				in="\n����:"+(callSomeOneEndTimeSelfGet-callSomeOneStartTimeSelfGet);
			}
		}
		
		return getCallTypeName(getType())
				+"\n����"+"��ʼʱ��DB��"+DateUtil.getTimeString(startTimeDB)
				+"\n����"+"��ʼʱ��self��"+DateUtil.getTimeString(callSomeOneStartTimeSelfGet)
				+(callSomeOneItsRingTimeSelfGet>0?"\n�Է�����ȷ��ʱ�䣺"+DateUtil.getTimeString(callSomeOneItsRingTimeSelfGet):"")
				+(callSomeOneItsRingTimeSelfGet>0?" ��ʱ��"+(callSomeOneItsRingTimeSelfGet-callSomeOneStartTimeSelfGet):"")
				+(callSomeOneItsCallingTimeSelfGet>0?"\nͨ����ʼʱ�䣺"+DateUtil.getTimeString(callSomeOneItsCallingTimeSelfGet):"")
				+(callSomeOneItsCallingTimeSelfGet>0?" ��ʱ��"+(callSomeOneItsCallingTimeSelfGet-callSomeOneItsRingTimeSelfGet):"")
				+"\n����DB��"+lastTime
				+in;
	}

	// ȫ�������Լ���ȡ��
	volatile long callSomeOneStartTimeSelfGet, callSomeOneItsRingTimeSelfGet,
			callSomeOneItsCallingTimeSelfGet, callSomeOneEndTimeSelfGet;

	public PhoneDateDuration setGetTime(long callSomeOneStartTimeSelfGet,
			long callSomeOneEndTimeSelfGet, long callSomeOneItsRingTimeSelfGet,
			long callSomeOneItsCallingTimeSelfGet) {
		// TODO Auto-generated method stub
		this.callSomeOneStartTimeSelfGet = callSomeOneStartTimeSelfGet;
		if (callSomeOneStartTimeSelfGet < startTimeDB) {
			Su.LogE("��ʼʱ�� DB�ı�self����");
		}
		this.callSomeOneItsRingTimeSelfGet = callSomeOneItsRingTimeSelfGet;
		this.callSomeOneItsCallingTimeSelfGet = callSomeOneItsCallingTimeSelfGet;
		this.callSomeOneEndTimeSelfGet = callSomeOneEndTimeSelfGet;
		Su.LogW(getInfo());

		return this;
	}
}