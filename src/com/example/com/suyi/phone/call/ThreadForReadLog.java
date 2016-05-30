package com.example.com.suyi.phone.call;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.phone.call.activity.StartActivity;
import com.example.com.suyi.phone.call.app.CallApp;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.Su;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;
import com.stericson.RootTools.execution.Shell;

public class ThreadForReadLog implements Runnable {

	final String regex = "onConnectedInOrOut: connectTime=(\\d+)";
	final String CDMA_kaiShiTongHua = "UNSOL_CDMA_INFO_REC";// 开始通话
	String cmsClear[] = new String[] { "logcat", " -c", " radio" };
	String cms[] = new String[] { "logcat", "-b", "radio" };

	final Pattern pattern = Pattern.compile(regex);

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSSZ");

	private static CharSequence formatTimeStr(long inTimeInMillis) {
		return DATE_FORMAT.format(new Date(inTimeInMillis));
	}

	private static final int CONNECT_TIME_DELAY = 2000;
	public static final int Err_Times_Max = 6;
	volatile int errTimes = 0;
	volatile Process mProcess = null;
	volatile Command mCommand;
	volatile Shell s;
	volatile boolean isRuning = true;
	FaceCallBack<Long> phoneReceiver;
	TelephonyManager tm;
	Context mContext;
	ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	PhoneLogCollection mPhoneLogCollection;

	volatile boolean isGetPhoneLog;
	boolean isCdma;

	/**
	 * @param phoneReceiver
	 * @param tm
	 */
	public ThreadForReadLog(FaceCallBack<Long> phoneReceiver, Context mContext,
			TelephonyManager tm) {
		// TODO Auto-generated constructor stub
		this.phoneReceiver = phoneReceiver;
		this.mContext = mContext;
		this.tm = tm;
		isCdma = isCDMASim();

		boolean isSDKVersionBelowJellyBean = SharePreferenceutils.getBoolean(
				mContext, "isSDKVersionBelowJellyBean", false);
		if (isSDKVersionBelowJellyBean) {
			isUseRootShell = false;
		}

		boolean isHasGetPhoneLog = SharePreferenceutils.getBoolean(mContext,
				"isHasGetPhoneLog", false);
		if (isHasGetPhoneLog) {
			isGetPhoneLog = false;
		}

		Su.LogW("isCdma:" + isCdma + " isUseRootShell" + isUseRootShell
				+ " isGetPhoneLog" + isGetPhoneLog);

	}

	/*
	 * China Mobile: 46000, 46002, 46007 China Unicom: 46001 China Telecom:46003
	 */
	private boolean isCDMASim() {
		int simState = tm.getSimState();
		if (simState != TelephonyManager.SIM_STATE_READY) {
			return false;
		}
		String simOp = tm.getSimOperator();
		return "46003".equals(simOp);
	}

	@Override
	public void run() {
		Su.LogW("启动 logcat read ");
		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		errTimes = 0;
		timeGet = 0;
		statrt();
	}

	private void statrt() {
		if (errTimes >= Err_Times_Max) {
			notifyView("多次读取错误，已停止");
			return;
		}

		Runtime mRuntime = Runtime.getRuntime();
		clearLog(mRuntime);
		readLog(mRuntime);
	}

	boolean isUseRootShell = true;

	private void clearLog(Runtime mRuntime) {
		if (!isRuning)
			return;
		Su.LogW("clearLog start");
//		if (isUseRootShell) {
//			try {
//				s = RootTools.getShell(true);
//				mCommand = new Command(100, 110, cmsClear) {
//					@Override
//					public void output(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//						
////						 mCommand.commandFinished(100);
//					}
//				};
//				s.runRootCommand(mCommand);
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (RootDeniedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				try {
//					if (mCommand != null)
//						mCommand.commandFinished(100);
//					if (s != null)
//						s.close();
//
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		} else {
			try {
				mProcess = mRuntime.exec(cmsClear);
				Su.LogW("log c  iiiiiii");
				// mProcess.waitFor();
				Su.LogW("log c end iiiiiii");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// finally {
			// if (mProcess != null) {
			// mProcess.destroy();
			// }
			// }
//		}
		Su.LogW("clearLog end");

	}

	public void startRead() {
		isRuning = true;
		singleThreadExecutor.submit(this);
	}

	public void endRead() {
		if (!isRuning)
			return;
		isRuning = false;
		if (mProcess != null)
			mProcess.destroy();
		mProcess = null;

		if (mCommand != null) {
			mCommand.commandFinished(100);
			mCommand.commandFinished(101);
			mCommand = null;
		}
		try {
			if (s != null) {
				s.close();
				s = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (mPhoneLogCollection != null && isGetPhoneLog) {
			mPhoneLogCollection.sendToServer();
		}

	}

	void readLog(Runtime mRuntime) {
		if (!isRuning)
			return;
		Su.LogW("readLog start");

		if (isGetPhoneLog) {
			mPhoneLogCollection = new PhoneLogCollection();
			mPhoneLogCollection.addLog(CallApp.getInstance().phoneInfo);
			mPhoneLogCollection
					.addLog("网络:"
							+ tm.getSimOperator()
							+ "; China Unicom:46001; China Telecom:46003;China Mobile:46000,46002,46007");
		}

//		if (!isUseRootShell) {
			InputStream lips = null;
			InputStreamReader lisr = null;
			BufferedReader lbfr = null;
			try {
				mProcess = mRuntime.exec(cms);
				lips = mProcess.getInputStream();
				lisr = new InputStreamReader(lips);
				lbfr = new BufferedReader(lisr);
				String line = null;
				while (isRuning && (line = lbfr.readLine()) != null) {
					// notifyView(line);
					//
					logLine(pattern, line);
				}

				mProcess.waitFor();
			} catch (Exception e) {
				notifyView("读取e :" + e.getMessage());
				errTimes++;
				statrt();
			} finally {
				notifyView("log thread end ");
				try {
					if (lips != null)
						lips.close();
					if (lisr != null)
						lisr.close();
					if (lbfr != null)
						lbfr.close();
				} catch (Exception e2) {
				}
			}
//		} else {
//
//			try {
//				Shell s = RootTools.getShell(true);
//				mCommand = new Command(101, cms) {
//					@Override
//					public void output(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//						logLine(pattern, arg1);
//					}
//				};
//				s.runRootCommand(mCommand);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (RootDeniedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				try {
//					if (mCommand != null)
//						mCommand.commandFinished(101);
//
//					if (s != null)
//						s.close();
//
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		Su.LogW("readLog end");

	}

	void notifyView(String info) {
		Intent mIntent = new Intent(StartActivity.CallStatus);
		mIntent.putExtra("status", info);
		mContext.sendBroadcast(mIntent);
	}

	volatile long timeGet = 0;
	volatile boolean isWaitingCDMA;// 临时状态 已经获取到一次 ，
	public static long CDMA_REC = 1000000l;
	public static long REC = 2000000l;

	// 酷派3次

	private void logLine(Pattern pattern, String line) {
		if (StringUtile.isEmpty(line))
			return;
		if (isGetPhoneLog) {
			mPhoneLogCollection.addLog(line);
		}
//		 Su.LogW("&&&&$$$$*****"+line);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			long time = System.currentTimeMillis();
			long connectTime = Long.valueOf(matcher.group(1));
			if (Math.abs(time - connectTime) <= CONNECT_TIME_DELAY) {
				timeGet++;
				if (timeGet == 1) {// 有的手机 发送多次
					phoneReceiver.callback(timeGet, connectTime);
					if (isCdma) {
						isWaitingCDMA = true;
					}
				}
				Su.LogW(timeGet + " " + line);
				Su.LogW("now     time -> " + formatTimeStr(time));
				Su.LogW("connect time -> " + formatTimeStr(connectTime));
			}
		} else {
			if (isCdma) {
				if (isWaitingCDMA) {
					if (isWaitingCDMA && line.contains(CDMA_kaiShiTongHua)) {
						Su.LogW("开始通话：" + line);
						phoneReceiver.callback(CDMA_REC, new Date().getTime());
						isWaitingCDMA = false;
					}
				}
			}
		}
	}

	public void trueEnd() {
		// TODO Auto-generated method stub
		singleThreadExecutor.shutdown();
	}
}
