package com.example.com.suyi.phone.call.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.com.suyi.base.SuActivity;
import com.example.com.suyi.domain.SuContextErrException;
import com.example.com.suyi.domain.PhoneStatusUsedMode;
import com.example.com.suyi.phone.call.AppPermissionManger;
import com.example.com.suyi.phone.call.app.CallApp;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.Su;
import com.example.com.suyi.phone.call.util.ViewUtil;
import com.tencent.bugly.crashreport.CrashReport;

public class ForLogPermissionActivity extends SuActivity {

	// private static final int DLG_COLLECT_LOG_OK = 1000;
	// private static final int DLG_COLLECT_LOG_FAIL = 2000;
	// private CollectLogTask mCollectLogTask;

	private static final int DLG_READ_LOG_PERMISSION_POLICY = 3000;
	private static final int DLG_GRANT_READ_LOG_PERMISSION_PROGRESS = 4000;
	private static final int DLG_GRANT_READ_LOG_PERMISSION_RESULT = 5000;

	private static final int No_Permission = 800;

	static boolean isNeedRoot = true;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// CrashReport.testJavaCrash();

		try {
			AppPermissionManger.getSingleton().checkPermissionGranted();
		} catch (SuContextErrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Bundle bundle = new Bundle();
			bundle.putString("result", e.getMessage());
			showDialog(No_Permission, bundle);

			return;
		}

		if (CallApp.getInstance().isSDKVersionBelowJellyBean()) {
			Su.LogW("SDK version below Jelly Bean, skip READ_LOGS permission check");
			SharePreferenceutils.putBoolean(mContext,
					"isSDKVersionBelowJellyBean", true);
			startLogView();
			return;
		}

		if (AppPermissionManger.getSingleton().isReadLogsPermissionGranted()) {
			Su.LogW("READ_LOGS permission granted");
			startLogView();
			return;
		}

		if (!isNeedRoot) {
			ViewUtil.showToast(mContext, "����Ҫroot,����");
			startLogView();
			return;
		}

		showDialog(DLG_READ_LOG_PERMISSION_POLICY);
	}

	/**
	 * 
	 */
	private void startLogView() {
		// TODO Auto-generated method stub
		mContext.startActivity(new Intent(mContext, StartActivity.class));
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		switch (id) {
		// case DLG_COLLECT_LOG_OK: {
		// AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		// builder.setMessage("�ѱ�����־��\n/sdcard/CallPhone.log\n");
		// builder.setNegativeButton(android.R.string.ok, null);
		// return builder.create();
		// }
		//
		// case DLG_COLLECT_LOG_FAIL: {
		// AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		// builder.setMessage("�ռ���־ʧ��");
		// builder.setNeutralButton(android.R.string.ok, null);
		// return builder.create();
		// }
		case No_Permission: {
			String message = bundle.getString("result");
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(message + "");
			builder.setNegativeButton("����Ȩʹ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			return builder.create();
		}

		case DLG_GRANT_READ_LOG_PERMISSION_RESULT: {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			int result = bundle.getInt("result");
			switch (result) {
			case CallApp.OUTGOING_CALL_AVAILABLE:
				builder.setMessage("���� <i>READ_LOGS</i> Ȩ�޸�Ӧ�óɹ�");
				builder.setNegativeButton("ȷ��", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						SharePreferenceutils.putInt(mContext, "usedMode",
								PhoneStatusUsedMode.LogMode);
						startLogView();
					}
				});
				break;
			case CallApp.OUTGOING_CALL_UNAVAILABLE_NO_SU:
				builder.setMessage("����豸û�б�<b>ROOT</b>");
				builder.setNegativeButton("ȷ��", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				break;
			case CallApp.OUTGOING_CALL_UNAVAILABLE_ACCESS_NOT_GIVEN:
				builder.setMessage("�޷��л���<b>ROOT</b>");
				builder.setNegativeButton("ȷ��", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();

					}
				});
				break;
			default:
				builder.setMessage("����Ȩ�޸�Ӧ��ʧ��");
				builder.setNegativeButton("������", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				break;
			}
			return builder.create();
		}

		case DLG_READ_LOG_PERMISSION_POLICY: {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle(android.R.string.dialog_alert_title);
			builder.setMessage("Ӧ����Ҫ <i>READ_LOGS</i> Ȩ��ʵ�ֽ���, "
					+ "������<b>Jelly Bean</b>�� Google �޸��˸�Ȩ�޵Ĳ��ԣ���ͨ������Ӧ��Ĭ�϶�����ʹ�ø�Ȩ�ޣ�"
					+ "����������<b>ROOT</b>���Ȩ������Ӧ��</string>");
			builder.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							new GrantPermissionTask().execute((Void) null);
						}
					});
			builder.setNegativeButton(android.R.string.cancel,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// setOutgoingCallPrefsEnabled(false);
							finish();
						}
					});

			return builder.create();
		}
		case DLG_GRANT_READ_LOG_PERMISSION_PROGRESS: {
			ProgressDialog progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage(">�������� <i>READ_LOGS</i> Ȩ�޸�Ӧ�á�");
			progressDialog.setCancelable(false);
			return progressDialog;
		}
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.finish();
	}

	@SuppressLint("NewApi")
	private class GrantPermissionTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			showDialog(DLG_GRANT_READ_LOG_PERMISSION_PROGRESS);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			return CallApp.getInstance().checkOutgoingCallFeasible();
		}

		@Override
		protected void onPostExecute(Integer result) {

			dismissDialog(DLG_GRANT_READ_LOG_PERMISSION_PROGRESS);
			Bundle bundle = new Bundle();
			bundle.putInt("result", result);
			showDialog(DLG_GRANT_READ_LOG_PERMISSION_RESULT, bundle);
		}
	}

	// private class CollectLogTask extends AsyncTask<ArrayList<String>, Void,
	// Boolean> {
	//
	// private ProgressDialog mProgressDialog;
	// private String mAdditionalInfo;
	//
	// public CollectLogTask(String additionalInfo) {
	// super();
	// mAdditionalInfo = additionalInfo;
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// showProgressDialog("�����ռ���־����ȴ���");
	// }
	//
	// void showProgressDialog(String message) {
	// mProgressDialog = new ProgressDialog(mContext);
	// mProgressDialog.setIndeterminate(true);
	// mProgressDialog.setMessage(message);
	// mProgressDialog.setCancelable(false);
	// mProgressDialog.show();
	// }
	//
	// @Override
	// protected Boolean doInBackground(ArrayList<String>... params) {
	// final StringBuilder log = new StringBuilder();
	// FileWriter fw = null;
	// if (mAdditionalInfo != null) {
	// SuLog.LogW("Add additional info");
	// log.append(mAdditionalInfo);
	// log.append(LINE_SEPARATOR);
	// }
	//
	// try {
	// ArrayList<String> commandLine = new ArrayList<String>();
	//				commandLine.add("logcat");//$NON-NLS-1$
	//				commandLine.add("-d");//$NON-NLS-1$
	// commandLine.add("-s");
	// commandLine.add("CallApp:D");
	// ArrayList<String> arguments = ((params != null) && (params.length > 0)) ?
	// params[0]
	// : null;
	// if (null != arguments) {
	// commandLine.addAll(arguments);
	// }
	//
	// String[] commandArray = commandLine.toArray(new String[0]);
	//
	// SuLog.LogW("Try to execute: " + Arrays.toString(commandArray));
	//
	// Process process = Runtime.getRuntime().exec(commandArray);
	// BufferedReader bufferedReader = new BufferedReader(
	// new InputStreamReader(process.getInputStream()));
	//
	// String line;
	// while ((line = bufferedReader.readLine()) != null) {
	// log.append(line);
	// log.append(LINE_SEPARATOR);
	// }
	//
	// SuLog.LogW("Collect log OK");
	//
	// File appFile = new File(
	// Environment.getExternalStorageDirectory(), APP_LOG);
	//
	// SuLog.LogW("Open file to write log: "
	// + appFile.getAbsolutePath());
	//
	// fw = new FileWriter(appFile);
	//
	// SuLog.LogW("Open file OK");
	//
	// fw.write(log.toString());
	//
	// SuLog.LogW("Write log OK");
	//
	// return true;
	// } catch (IOException e) {
	// SuLog.LogW("CollectLogTask.doInBackground failed"
	// + e.getMessage());
	// return false;
	// } finally {
	// if (fw != null) {
	// try {
	// fw.close();
	// } catch (IOException e) {
	// }
	// }
	// }
	// }
	//
	// @Override
	// protected void onPostExecute(Boolean result) {
	// dismissProgressDialog();
	// if (result) {
	// showDialog(DLG_COLLECT_LOG_OK);
	// } else {
	// showDialog(DLG_COLLECT_LOG_FAIL);
	// }
	// }
	//
	// private void dismissProgressDialog() {
	// if (null != mProgressDialog && mProgressDialog.isShowing()) {
	// mProgressDialog.dismiss();
	// mProgressDialog = null;
	// }
	// }
	// }
}
