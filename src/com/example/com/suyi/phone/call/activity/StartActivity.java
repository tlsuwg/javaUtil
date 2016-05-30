package com.example.com.suyi.phone.call.activity;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.base.SuActivity;
import com.example.com.suyi.domain.ConstantStatic;
import com.example.com.suyi.phone.call.R;
import com.example.com.suyi.phone.call.ThreadForPlayVoice;
import com.example.com.suyi.phone.call.app.CallApp;
import com.example.com.suyi.phone.call.app.CallService;
import com.example.com.suyi.phone.call.util.FileUtil;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;
import com.example.com.suyi.phone.call.util.ViewUtil;

public class StartActivity extends SuActivity {

	public static final String CallStatus = "com.suyi.call.status";

	EditText input_msg_text, input_msg_time, input_temperature_text;
	Button input_some_one_button, settingOK, input_voice_button, stopButton,
			settingL, test_voice_button;

	TextView input_msg_length_text, log, contacts_size_text, voice_text;

	CheckBox send_msg_CheckBox;
	LinearLayout set_msg_lin;

	private String settingVoice;
	private boolean isSetSendMes;
	private int mContactsSize = -1;
	private String msg;
	private int callTime, temperature;

	List<String> logList = new LinkedList<>();

	Context mContext;
	ThreadForPlayVoice mThreadForPlayVoice;
	FaceCallBack mFaceCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		input_msg_text = (EditText) findViewById(R.id.input_msg_text);
		input_msg_time = (EditText) findViewById(R.id.input_msg_time);

		input_some_one_button = (Button) findViewById(R.id.input_some_one_button);
		settingOK = (Button) findViewById(R.id.settingOK);
		input_voice_button = (Button) findViewById(R.id.input_voice_button);
		test_voice_button = (Button) findViewById(R.id.test_voice_button);

		input_msg_length_text = (TextView) findViewById(R.id.input_msg_length_text);
		log = (TextView) findViewById(R.id.log);
		contacts_size_text = (TextView) findViewById(R.id.contacts_size_text);
		voice_text = (TextView) findViewById(R.id.voice_text);

		send_msg_CheckBox = (CheckBox) findViewById(R.id.send_msg_CheckBox);
		set_msg_lin = (LinearLayout) findViewById(R.id.set_msg_lin);
		settingL = (Button) findViewById(R.id.settingL);

		isSetSendMes = SharePreferenceutils.getBoolean(this, "isSetSendMes",
				false);
		input_temperature_text = (EditText) findViewById(R.id.input_temperature_text);

		stopButton = (Button) findViewById(R.id.stopButton);
		stopButton.setVisibility(View.GONE);

		msg = SharePreferenceutils.getString(this, ConstantStatic.msg, "");
		if (!StringUtile.isEmpty(msg)) {
			input_msg_text.setText(msg);
		}

		callTime = SharePreferenceutils
				.getInt(this, ConstantStatic.callTime, 5);
		if (callTime < 0) {
			input_msg_time.setText("5");
		} else {
			input_msg_time.setText("" + callTime);
		}

		temperature = SharePreferenceutils.getInt(this,
				ConstantStatic.temperature, 60);
		if (temperature < 0) {
			input_temperature_text.setText("60");
		} else {
			input_temperature_text.setText("" + temperature);
		}

		send_msg_CheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						isSetSendMes = isChecked;
						if (isSetSendMes) {
							set_msg_lin.setVisibility(View.VISIBLE);
						} else {
							set_msg_lin.setVisibility(View.GONE);
						}
						SharePreferenceutils.putBoolean(mContext,
								"isSetSendMes", isSetSendMes);
					}
				});
		send_msg_CheckBox.setChecked(isSetSendMes);

		showVoiceStatus();

		settingOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isSetSendMes) {

					String time = input_msg_time.getEditableText().toString();
					if (StringUtile.isEmpty(time)) {
						ViewUtil.showToast(mContext, "必须设置时间");
					}
					callTime = Integer.parseInt(time);
					SharePreferenceutils.putInt(mContext,
							ConstantStatic.callTime, callTime);

					// -================
					String msgs = input_msg_text.getEditableText().toString();
					if (StringUtile.isEmpty(msgs)) {
						ViewUtil.showToast(mContext, "必须设置信息");
					}
					msg = msgs;
					SharePreferenceutils.putString(mContext,
							ConstantStatic.msg, msg);

					// -================
				}

				// -================
				if (mContactsSize <= 0) {
					ViewUtil.showToast(mContext, "请设置联系人");
					return;
				}
				// -================
				if (StringUtile.isEmpty(settingVoice)) {
					ViewUtil.showToast(mContext, "请设置语音文件");
					return;
				}
				// -================

				String tempInfo = input_temperature_text.getEditableText()
						.toString();
				if (StringUtile.isEmpty(tempInfo)) {
					ViewUtil.showToast(mContext, "请设置温度");
					return;
				}

				temperature = Integer.parseInt(tempInfo);
				if (temperature <= 50) {
					ViewUtil.showToast(mContext, "请设置温度高于50");
					return;
				}
				SharePreferenceutils.putInt(mContext, "temperature",
						temperature);

				doCall();

				stopButton.setVisibility(View.VISIBLE);
				settingOK.setVisibility(View.GONE);
			}

		});

		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mContext.stopService(new Intent(mContext, CallService.class));

				stopButton.setVisibility(View.GONE);
				settingOK.setVisibility(View.VISIBLE);
			}
		});

		settingL.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doCall();

			}
		});

		input_voice_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					getFile();
				} catch (Exception e) {
					// TODO: handle exception
					ViewUtil.showToast(mContext, "亲，你连个文件选择器都木有啊");
				}

			}
		});

		input_some_one_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(mContext, SettingConActivity.class));
			}
		});

		test_voice_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mFaceCallBack==null)
				 mFaceCallBack = new FaceCallBack<Object>() {

					@Override
					public boolean callback(Object... ts) {
						// TODO Auto-generated method stub
						if (ts == null) {// 播放完毕
							// stopCall();
						} else {
							if (ts != null && ts.length >= 1
									&& ts[0] instanceof Exception) {
								// 播放出现问题
								final Exception e = (Exception) ts[0];
								mHandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										ViewUtil.showToast(
												mContext,
												"播放音频文件出错。技术细节："
														+ e.getMessage());
									}
								});
							}
						}
						closePlayer();
						return false;
					}

				};

				try {
					mThreadForPlayVoice = new ThreadForPlayVoice(mContext,
							mFaceCallBack);
					mThreadForPlayVoice.startPlay();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ViewUtil.showToast(mContext, e.getMessage());
				}
			}
		});
	}

	void closePlayer() {
		if (mThreadForPlayVoice != null) {
			mThreadForPlayVoice.trueEnd();
			mThreadForPlayVoice = null;
		}
	}

	// 选取图片按钮单击事件
	public void getFile() {
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		// intent.setType("image/*");
		intent.setType("audio/*"); // 选择音频
		// intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
		// intent.setType("video/*;image/*");//同时选择视频和图片
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 选取图片的返回值
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();

				String filepath = FileUtil.getRealFilePath(this, uri);
				File file = new File(filepath);
				if (file.isFile() && file.exists()) {
					Su.LogW(filepath);
					settingVoice = file.getAbsolutePath();
					SharePreferenceutils.putString(this,
							ConstantStatic.settingVoice, filepath);
					voice_text.setText("已经设置");
					test_voice_button.setVisibility(View.VISIBLE);
				}
			} else {
				// ViewUtil.showToast(this, "失败");
			}
		}
	}

	/**
	 * 
	 */
	private void showContactsSize() {
		// TODO Auto-generated method stub
		String mContacts = SharePreferenceutils.getString(mContext,
				ConstantStatic.mContacts, "");

		if (StringUtile.isEmpty(mContacts)) {
			contacts_size_text.setText("必须设置");
			mContactsSize = -1;
		} else {
			contacts_size_text.setText("已经设置");
			mContactsSize = 2;
		}

	}

	BroadcastReceiver mBroadcastReceiver;
	IntentFilter mIntentFilter;

	private void doCall() {
		// TODO Auto-generated method stub

		if (mBroadcastReceiver == null) {
			mBroadcastReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					showCallStatus(intent);
				}
			};

			mIntentFilter = new IntentFilter();
			mIntentFilter.addAction(CallStatus);
		}

		mContext.registerReceiver(mBroadcastReceiver, mIntentFilter);
		mContext.startService(new Intent(mContext, CallService.class));

	}

	private void showCallStatus(Intent intent) {
		// TODO Auto-generated method stub
		if (intent == null)
			return;

		String info = intent.getStringExtra("status");
		if (StringUtile.isEmpty(info)) {

		} else {
			showLog(info, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showContactsSize();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mBroadcastReceiver != null) {
			mContext.unregisterReceiver(mBroadcastReceiver);
		}
		super.onDestroy();

	}

	/**
	 * @param info
	 */
	private void showLog(String info, boolean isLog) {
		// TODO Auto-generated method stub
		if (logList.size() > 5000) {
			logList.remove(0);
		}
		logList.add(info);

		if (isOnTop) {
			StringBuffer sb = new StringBuffer();
			for (String info1 : logList) {
				sb.append(info1).append("\n");
			}

			log.setText(sb.toString());
			if (isLog)
				Su.LogW(sb.toString());
		}
	}

	/**
	 * 
	 */
	private void showVoiceStatus() {
		// TODO Auto-generated method stub
		settingVoice = SharePreferenceutils.getString(this,
				ConstantStatic.settingVoice, "");
		if (StringUtile.isEmpty(settingVoice)) {
			voice_text.setText("必须设置");
		} else {
			voice_text.setText("已经设置");
		}
	}

}
