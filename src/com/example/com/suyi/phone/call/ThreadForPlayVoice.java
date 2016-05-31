package com.example.com.suyi.phone.call;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.PowerManager;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.domain.ConstantStatic;
import com.example.com.suyi.phone.call.activity.StartActivity;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.Su;

public class ThreadForPlayVoice implements Runnable {

	FaceCallBack phoneReceiver;
	Context mContext;
	ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	String settingVoice;
	MediaPlayer mMediaPlayer;
	AudioManager audioManager;

	void setgetStreamVolume() {
		int max = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
		audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, 0);
		int current = audioManager
				.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
		Su.LogW("call  max : " + max + " current : " + current);

		// 音乐音量
		max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max,0);
		current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		Su.LogW("MUSIC  max : " + max + " current : " + current);

	}

	/**
	 * @param phoneReceiver
	 * @param tm
	 * @throws Exception
	 */
	public ThreadForPlayVoice(Context mContext, FaceCallBack phoneReceiver)
			throws Exception {
		// TODO Auto-generated constructor stub
		this.phoneReceiver = phoneReceiver;
		this.mContext = mContext;
		settingVoice = SharePreferenceutils.getString(mContext,
				ConstantStatic.settingVoice, "");
		checkFile();

		audioManager = (AudioManager) mContext
				.getSystemService(Service.AUDIO_SERVICE);

		// audioManager.requestAudioFocus(new OnAudioFocusChangeListener() {
		//
		// @Override
		// public void onAudioFocusChange(int focusChange) {
		// switch (focusChange) {
		// case AudioManager.AUDIOFOCUS_GAIN:
		// Su.LogW("焦点：AUDIOFOCUS_GAIN");
		// // resume playback
		// // if (mMediaPlayer == null)
		// // initMediaPlayer();
		// // else if (!mMediaPlayer.isPlaying())
		// // mMediaPlayer.start();
		// break;
		//
		// case AudioManager.AUDIOFOCUS_LOSS:
		// // Lost focus for an unbounded amount of time: stop playback
		// // and release media player
		// Su.LogW("焦点：AUDIOFOCUS_LOSS");
		// stop();
		// forceAnyWay();
		// break;
		//
		// case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
		// // Lost focus for a short time, but we have to stop
		// // playback. We don't release the media player because
		// // playback
		// // is likely to resume
		// Su.LogW("焦点：AUDIOFOCUS_LOSS_TRANSIENT");
		// stop();
		// forceAnyWay();
		// // if (mMediaPlayer.isPlaying())
		// // mMediaPlayer.pause();
		// break;
		//
		// case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
		// // Lost focus for a short time, but it's ok to keep playing
		// // at an attenuated level
		// // if (mMediaPlayer.isPlaying())
		// // mMediaPlayer.setVolume(0.1f, 0.1f);
		// stop();
		// forceAnyWay();
		// Su.LogW("焦点：AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
		// break;
		// }
		// }
		//
		// private void forceAnyWay() {
		// // TODO Auto-generated method stub
		// Su.LogW("继续抢夺");
		// int result = audioManager.requestAudioFocus(this,
		// AudioManager.STREAM_MUSIC,
		// AudioManager.AUDIOFOCUS_GAIN);
		//
		// if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
		// // could not get audio focus.
		//
		// }else{
		// Su.LogW("没有抢到");
		// }
		//
		// if(isRuning){
		// setgetStreamVolume();
		// statrt();
		// }
		//
		// }
		// }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
	}

	void checkFile() throws Exception {
		File settingVoiceFile = new File(settingVoice);
		if (settingVoiceFile == null || !settingVoiceFile.isFile()
				|| !settingVoiceFile.exists() || settingVoiceFile.length() == 0) {
			throw new Exception("播放文件不存在：" + settingVoice);
		}
	}

	@Override
	public void run() {
		Su.LogW("启动 ThreadForPlayVoice  ");
		statrt();
	}

	private void statrt() {
		try {
			if (mMediaPlayer != null) {
				mMediaPlayer.stop();
			} else {
				init();
			}
			Su.LogW("播放" + settingVoice);
			setgetStreamVolume();
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			phoneReceiver.callback(e);
			Su.MonitoringException(e, "2016_5_28_21_37", "播放音频MediaPlayer出现问题");
		}

	}

	private void init() throws IllegalArgumentException, SecurityException,
			IllegalStateException, IOException {
		// TODO Auto-generated method stub
		
//		使用外放 setSpeakerphoneOn  可以听到 但是不清楚

		mMediaPlayer = new MediaPlayer();
		
//		setAudioStreamType 设置什么都OK 但是测试的时候 需要声明下 外放music 
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//打电话的时候貌似这个没有作用了  也是使用setSpeakerphoneOn
//		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
//		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_SYSTEM);

		
//	    audioManager.setSpeakerphoneOn(true);//打开了外放 对于电话来说的  拨出电话使用这个

	    //		audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
		mMediaPlayer.setDataSource(settingVoice);
		mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
		mMediaPlayer.setVolume(1.0f, 1.0f);
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				Su.LogW("播放完成");
				stop();
				phoneReceiver.callback(null);
			}
		});

		mMediaPlayer.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				Exception e = new Exception("MediaPlayer what:" + what
						+ " extra:" + extra);
				phoneReceiver.callback(e);
				Su.MonitoringException(e, "2016_5_29_11_30",
						"播放音频MediaPlayer出现问题OnErrorListener");
				return false;
			}
		});

	}

	public void stop() {
		Su.LogW("stop播放" + settingVoice);
		if (mMediaPlayer != null)
			mMediaPlayer.stop();
		audioManager.setMode(AudioManager.MODE_NORMAL);
	}

	boolean isRuning;

	public void startPlay() {
		isRuning = true;
		singleThreadExecutor.submit(this);
	}

	void notifyView(String info) {
		Intent mIntent = new Intent(StartActivity.CallStatus);
		mIntent.putExtra("status", info);
		mContext.sendBroadcast(mIntent);
	}

	public void trueEnd() {
		isRuning = false;
		// TODO Auto-generated method stub
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		singleThreadExecutor.shutdown();
	}
}
