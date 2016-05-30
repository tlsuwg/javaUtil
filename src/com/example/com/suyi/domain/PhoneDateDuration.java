package com.example.com.suyi.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.com.suyi.phone.call.app.PhoneReceiver;
import com.example.com.suyi.phone.call.util.DateUtil;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;

public class PhoneDateDuration {

	static final int RingToGetTime = 1000;// 怎么也需要1秒才能接听啊

	public static final int Call_UnKnoe = 1;// 通话
	public static final int OutNoRing = 2;// 连呼叫都没有成功
	public static final int OutOnlyHas = 3;// 只是响铃
	public static final int OutInCall = 4;// 通话

	static String CallStatsName[] = new String[] { "不清楚的状态？", "连呼叫都没有成功",
			"只是响铃", "通话" };

	// 电话号码是可以使用的(对方网络空口判断)，对方电话可以在线

	// A可能是电话卡被拔掉，没有注销，不在服务区，无信号
	// B可以是关机 注销掉服务
	// C也可以是自己的号码，呼叫忙
	// D正在通话

	// 持续时间 可能只是呼出 （时间是0），呼出之后的等待对方接通的时间，也可能真的是通话时间

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
			in="\n呼叫:"+(callSomeOneItsRingTimeSelfGet-callSomeOneStartTimeSelfGet)
				+"\n响铃："+(callSomeOneItsCallingTimeSelfGet-callSomeOneItsRingTimeSelfGet)
				+"\n通话"+(callSomeOneEndTimeSelfGet-callSomeOneItsCallingTimeSelfGet);
			
		}else{
			if(callSomeOneItsRingTimeSelfGet>0){
				in="\n呼叫:"+(callSomeOneItsRingTimeSelfGet-callSomeOneStartTimeSelfGet)
						+"\n响铃："+(callSomeOneEndTimeSelfGet-callSomeOneItsRingTimeSelfGet);
			}else{
				in="\n呼叫:"+(callSomeOneEndTimeSelfGet-callSomeOneStartTimeSelfGet);
			}
		}
		
		return getCallTypeName(getType())
				+"\n——"+"开始时间DB："+DateUtil.getTimeString(startTimeDB)
				+"\n——"+"开始时间self："+DateUtil.getTimeString(callSomeOneStartTimeSelfGet)
				+(callSomeOneItsRingTimeSelfGet>0?"\n对方网关确认时间："+DateUtil.getTimeString(callSomeOneItsRingTimeSelfGet):"")
				+(callSomeOneItsRingTimeSelfGet>0?" 耗时："+(callSomeOneItsRingTimeSelfGet-callSomeOneStartTimeSelfGet):"")
				+(callSomeOneItsCallingTimeSelfGet>0?"\n通话开始时间："+DateUtil.getTimeString(callSomeOneItsCallingTimeSelfGet):"")
				+(callSomeOneItsCallingTimeSelfGet>0?" 耗时："+(callSomeOneItsCallingTimeSelfGet-callSomeOneItsRingTimeSelfGet):"")
				+"\n持续DB："+lastTime
				+in;
	}

	// 全部都是自己获取的
	volatile long callSomeOneStartTimeSelfGet, callSomeOneItsRingTimeSelfGet,
			callSomeOneItsCallingTimeSelfGet, callSomeOneEndTimeSelfGet;

	public PhoneDateDuration setGetTime(long callSomeOneStartTimeSelfGet,
			long callSomeOneEndTimeSelfGet, long callSomeOneItsRingTimeSelfGet,
			long callSomeOneItsCallingTimeSelfGet) {
		// TODO Auto-generated method stub
		this.callSomeOneStartTimeSelfGet = callSomeOneStartTimeSelfGet;
		if (callSomeOneStartTimeSelfGet < startTimeDB) {
			Su.LogE("开始时间 DB的比self的晚？");
		}
		this.callSomeOneItsRingTimeSelfGet = callSomeOneItsRingTimeSelfGet;
		this.callSomeOneItsCallingTimeSelfGet = callSomeOneItsCallingTimeSelfGet;
		this.callSomeOneEndTimeSelfGet = callSomeOneEndTimeSelfGet;
		Su.LogW(getInfo());

		return this;
	}
}