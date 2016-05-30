/**
 * 
 */
package com.example.com.suyi.phone.call;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;
import com.example.com.suyi.domain.SuContextErrException;
import com.example.com.suyi.phone.call.app.CallApp;
import com.example.com.suyi.phone.call.util.StringUtile;

/**
 * @author "suwg" 2016��5��30��
 */
public class CallUtil {

	public static void call(String number) throws SuContextErrException {
		if (StringUtile.isEmpty(number)) {
			throw new SuContextErrException("����Ϊnull");
		}

		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		CallApp.getInstance().startActivity(intent);
	}
	
	
	public void sendMsg(String number,String context) throws SuContextErrException{
		if (StringUtile.isEmpty(number)||StringUtile.isEmpty(context)) {
			throw new SuContextErrException("����Ϊnull");
		}
		
		SmsManager smsManger = SmsManager.getDefault();
	    smsManger.sendTextMessage(
	           number, //�ռ��˵ĺ���
	           null,//��������
	           context,//��������
	           null,//������ͳɹ�,�ص��ι㲥 
	           null);//���Է����ճɹ�,�ص��ι㲥
	}
	

    static String TAG = "PhoneUtils";
    /**
     * ��TelephonyManager��ʵ����ITelephony,������
     */
    static public ITelephony getITelephony(TelephonyManager telMgr)
            throws Exception {
        Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod(
                "getITelephony");
        getITelephonyMethod.setAccessible(true);// ˽�л�����Ҳ��ʹ��
        return (ITelephony) getITelephonyMethod.invoke(telMgr);
    }
     
    //�Զ�����
    public static void autoAnswerPhone(Context c,TelephonyManager tm) {
        try {
            Log.i(TAG, "autoAnswerPhone");
            ITelephony itelephony = getITelephony(tm);
            // itelephony.silenceRinger();
            itelephony.answerRingingCall();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Log.e(TAG, "����Android2.3��2.3���ϵİ汾��");
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                c.sendOrderedBroadcast(intent,
                        "android.permission.CALL_PRIVILEGED");
                intent = new Intent("android.intent.action.MEDIA_BUTTON");
                keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                c.sendOrderedBroadcast(intent,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent1.putExtra("state", 1);
                localIntent1.putExtra("microphone", 1);
                localIntent1.putExtra("name", "Headset");
                c.sendOrderedBroadcast(localIntent1,
                        "android.permission.CALL_PRIVILEGED");
                
                Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent2.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent1);
                c.sendOrderedBroadcast(localIntent2,
                        "android.permission.CALL_PRIVILEGED");
                
                Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent3.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent2);
                c.sendOrderedBroadcast(localIntent3,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent4.putExtra("state", 0);
                localIntent4.putExtra("microphone", 1);
                localIntent4.putExtra("name", "Headset");
                
                c.sendOrderedBroadcast(localIntent4,
                        "android.permission.CALL_PRIVILEGED");
            } catch (Exception e2) {
                e2.printStackTrace();
                Intent meidaButtonIntent = new Intent(
                        Intent.ACTION_MEDIA_BUTTON);
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                c.sendOrderedBroadcast(meidaButtonIntent, null);
            }
        }
    }
     
    //�Զ��Ҷ�
    public static void endPhone(Context c,TelephonyManager tm) {
        try {
            Log.i(TAG, "endPhone");
            ITelephony iTelephony;
            Method getITelephonyMethod = TelephonyManager.class
                    .getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            iTelephony = (ITelephony) getITelephonyMethod.invoke(tm,
                    (Object[]) null);
            // �Ҷϵ绰
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 


}
