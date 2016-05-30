/**
 * 
 */
package com.example.com.suyi.phone.call;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.domain.ConstantStatic;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.Su;

/**
 * @author "suwg" 2016年5月20日
 */
public class TempListener {

	Context mContext;
	FaceCallBack faceCallBack;
	int temperature;
	boolean isBelow=true;;

	public TempListener(Context mContext, FaceCallBack faceCallBack) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.faceCallBack = faceCallBack;
		temperature = SharePreferenceutils.getInt(mContext,
				ConstantStatic.temperature, 60);
	}

	SensorManager sensorManager;
	SensorEventListener mSensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float[] values = event.values;
			StringBuffer sb = new StringBuffer();
			for (float d : values) {
				sb.append(d).append(",");
			}
			Su.LogW(sb.toString());

			if(values[0]>=temperature){//高于
				if(isBelow){
					isBelow=false;
					if(faceCallBack!=null)faceCallBack.callback(isBelow,values[0]);
				}
			}else{
				if(!isBelow){//低于
					isBelow=true;
					if(faceCallBack!=null)faceCallBack.callback(isBelow,values[0]);
				}
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	};

	int getTempSensors[] = new int[] { Sensor.TYPE_AMBIENT_TEMPERATURE,
			Sensor.TYPE_TEMPERATURE };

	public Sensor getTempSensor() {
		for (int type : getTempSensors) {
			Sensor mSensor = sensorManager.getDefaultSensor(type);
			if (mSensor == null)
				continue;
			return mSensor;
		}
		return null;
	}

	// 采样率的类型：
	// SENSOR_DELAY_NOMAL (200000微秒)
	// SENSOR_DELAY_UI (60000微秒)
	// SENSOR_DELAY_GAME (20000微秒)
	// SENSOR_DELAY_FASTEST (0微秒)
	public void registerListener() {
		if (sensorManager == null) {
			sensorManager = (SensorManager) mContext
					.getSystemService(Context.SENSOR_SERVICE);
		}
		sensorManager.registerListener(mSensorEventListener,
		// TYPE_MAGNETIC_FIELD_UNCALIBRATED
				getTempSensor(), SensorManager.SENSOR_DELAY_NORMAL);

	}

	public void unregisterListener() {
		if (sensorManager != null)
			sensorManager.unregisterListener(mSensorEventListener);
	}

}
