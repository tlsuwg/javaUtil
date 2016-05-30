/**
 * 
 */
package com.example.com.suyi.domain;

import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.StringUtile;

import android.content.Context;

/**
 * @author "suwg" 2016年5月17日
 */
public class MsgSetting {
	// 设置时间\n（接听时间，超过该时间才发送短信,单位秒）\n（0代表必须接通即发送）;（-1代表接听即发送）;(-2代表一定会发送)
	public static final int MsgSetting_MustSend = -2;
	public static final int MsgSetting_MustAnswer = -1;
	public static final int MsgSetting_MustConnect = 0;
	// public static final int MsgSetting_MustAnswerTime = 1;

	Context mContext;

	/**
	 * @param mContext
	 */
	public MsgSetting(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	boolean isSetSendMes;
	int callTime;
	public String msg;

	/**
	 * @throws Exception
	 * 
	 */
	public void getSetting() throws Exception {
		// TODO Auto-generated method stub
		isSetSendMes = SharePreferenceutils.getBoolean(mContext,
				"isSetSendMes", false);
		if (isSetSendMes) {
			callTime = SharePreferenceutils.getInt(mContext, "callTime", 0);
			msg = SharePreferenceutils.getString(mContext, ConstantStatic.msg, "");
			if (!StringUtile.isEmpty(msg)) {
				throw new Exception("请设置短信内容");
			}
		}
	}

	public boolean isShouldCall(String phone, int Answertime) {
		switch (callTime) {
		case MsgSetting_MustConnect:
			return true;
		case MsgSetting_MustSend:
			return true;
		case MsgSetting_MustAnswer:
			return true;
		default:
			if (Answertime >= callTime)
				return true;
			break;
		}
		return false;
	};

}
