/**
 * 
 */
package com.example.com.suyi.phone.call.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.com.suyi.domain.ConstantStatic;
import com.example.com.suyi.phone.call.R;
import com.example.com.suyi.phone.call.util.FileUtil;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.StringUtile;
import com.example.com.suyi.phone.call.util.ViewUtil;

/**
 * @author "suwg" 2016��5��19��
 */
public class SettingConActivity extends Activity implements
		View.OnClickListener {

	public static String[] names = new String[] { "ɾ������������ϵ��", "���ļ��л�ȡ����ӣ�",
			"������������ݣ���ӣ�", "չʾ������ϵ��" };

	public static String ALLNumber = "^[0-9]*[1-9][0-9]*$";// ������
	Pattern pALLNumber = Pattern.compile(ALLNumber);
	Handler mHandler = new Handler();

	Context mContext;
	TextView log_text_view;
	LinearLayout left_lin, right_lin;
	ProgressBar mpro_;
	CheckBox delete_same_phone;
	private boolean isDeleteSamePhone;
	EditText get_same_phone_edit;
	Button goon_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		right_lin = (LinearLayout) findViewById(R.id.right_lin);
		left_lin = (LinearLayout) findViewById(R.id.left_lin);
		log_text_view = (TextView) findViewById(R.id.log_text_view);
		mpro_ = (ProgressBar) findViewById(R.id.mpro_);
		delete_same_phone = (CheckBox) findViewById(R.id.delete_same_phone);
		get_same_phone_edit = (EditText) findViewById(R.id.get_same_phone_edit);
		goon_add = (Button) findViewById(R.id.goon_add);

		for (String name : names) {
			Button mButton = new Button(this);
			mButton.setText(name);
			mButton.setTag(name);
			mButton.setOnClickListener(this);
			right_lin.addView(mButton);
		}

		isDeleteSamePhone = SharePreferenceutils.getBoolean(mContext,
				"isDeleteSamePhone", true);
		delete_same_phone.setChecked(isDeleteSamePhone);
		delete_same_phone
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						isDeleteSamePhone = isChecked;
						SharePreferenceutils.putBoolean(mContext,
								"isDeleteSamePhone", isChecked);
					}
				});

		showBaseLog();

		goon_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveInto();
			}
		});
	}

	int mContactsSize = 0;

	void showBaseLog() {
		{
			// int mContactsSize = SharePreferenceutils.getInt(mContext,
			// "mContactsSize", 0);
			// log_text_view.setText("��ϵ��������" + mContactsSize);

			String mContacts = SharePreferenceutils.getString(mContext,
					ConstantStatic.mContacts, "");
			if (!StringUtile.isEmpty(mContacts)) {
				String ss[] = mContacts.split(",");
				for (String info : ss) {
					if (StringUtile.isEmpty(info))
						continue;
					phoneSetAdd.add(info);
				}
			}

			log_text_view.setText("��ϵ���ų��ظ�������" + phoneSetAdd.size());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isLoading) {
			ViewUtil.showToast(mContext, "���ڷ�������ȴ�...");
			return;
		}

		String info = (String) v.getTag();
		if (StringUtile.isEmpty(info))
			return;
		if (info.equals(names[0])) {
			isDelete();
		} else if (info.equals(names[1])) {
			try {
				getFile();
			} catch (Exception e) {
				// TODO: handle exception
				ViewUtil.showToast(mContext, "�ף��������ļ�ѡ������ľ�а�");
			}
		} else if (info.equals(names[2])) {
			getEdit();
		} else if (info.equals(names[3])) {
			log_text_view.setText(SharePreferenceutils.getString(mContext,
					ConstantStatic.mContacts, ""));
		}
	}

	// ѡȡͼƬ��ť�����¼�
	public void getFile() {
		Intent intent = new Intent();
		/* ����Pictures����Type�趨Ϊimage */
		// intent.setType("image/*");
		// intent.setType("audio/*"); // ѡ����Ƶ
		// intent.setType("video/*"); //ѡ����Ƶ ��mp4 3gp ��android֧�ֵ���Ƶ��ʽ��
		// intent.setType("video/*;image/*");//ͬʱѡ����Ƶ��ͼƬ
		/* ʹ��Intent.ACTION_GET_CONTENT���Action */
		intent.setType("*/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(
				Intent.createChooser(intent, "Select a txt file"), 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ѡȡͼƬ�ķ���ֵ
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				String filepath = FileUtil.getRealFilePath(this, uri);
				File file = new File(filepath);
				if (file.isFile() && file.exists()) {
					getConFormFile(file);
				} else {
					ViewUtil.showToast(mContext, "�ļ�������");
				}
			}
		}
	}

	boolean isLoading;

	/**
	 * @param file
	 */

	HashSet<String> phoneSetAll = new HashSet<>();
	HashSet<String> phoneSetAdd = new HashSet<>();
	LinkedList<String> listPhoneAdd = new LinkedList<>();// ��ӵ�
	LinkedList<String> listLog = new LinkedList<>();
	boolean isHasNotMach;

	void getEdit() {

		final String getInfo = get_same_phone_edit.getEditableText().toString();
		if (StringUtile.isEmpty(getInfo)) {
			ViewUtil.showToast(mContext, "�븴�ƻ������ı���");
			return;
		}
		isLoading = true;
		mpro_.setVisibility(View.VISIBLE);
		log_text_view.setText("���ڷ���....");
		isHasNotMach = false;

		phoneSetAdd.clear();
		phoneSetAdd.addAll(phoneSetAll);
		listPhoneAdd.clear();
		listLog.clear();

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					sax(getInfo);
					onGetConAll();
				} catch (Exception e) {
					// TODO: handle exception
					onGetConErr(e);
				} finally {

				}
			}

		}).start();
	}

	private void getConFormFile(final File file) {
		// TODO Auto-generated method stub

		isLoading = true;
		mpro_.setVisibility(View.VISIBLE);
		log_text_view.setText("���ڷ���....");
		isHasNotMach = false;

		phoneSetAdd.clear();
		phoneSetAdd.addAll(phoneSetAll);
		listPhoneAdd.clear();
		listLog.clear();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(
							new FileInputStream(file)));

					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						sax(lineTxt);
					}

					onGetConAll();
				} catch (Exception e) {
					// TODO: handle exception
					onGetConErr(e);

				} finally {
					try {
						if (bufferedReader != null)
							bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

			}

		}).start();
	}

	/**
	 * @param lineTxt
	 */
	protected void sax(String lineTxt) {
		// TODO Auto-generated method stub

		lineTxt = lineTxt.trim();
		String ss[] = null;
		if (lineTxt.contains(",")) {
			ss = lineTxt.split(",");
		} else if (lineTxt.contains("��")) {
			ss = lineTxt.split("��");
		}

		if (ss != null && ss.length > 0) {
			for (String info : ss) {
				info = info.trim();
				if (StringUtile.isEmpty(info)) {
					continue;
				}

				if (isDeleteSamePhone && phoneSetAdd.contains(info)) {
					listLog.add("XX�ظ���" + info);
					continue;
				}

				if (pALLNumber.matcher(info).matches()) {
					if (isDeleteSamePhone) {
						if (!phoneSetAdd.contains(info)) {
							phoneSetAdd.add(info);
							listPhoneAdd.add(info);
							listLog.add(info);
						} else {
							listLog.add("XX�ظ���" + info);
						}
					} else {
						listPhoneAdd.add(info);
						listLog.add(info);
					}
					continue;
				} else {
					if (info.contains("-")) {
						String info2 = info.replace("-", "");
						if (isDeleteSamePhone && phoneSetAdd.contains(info2)) {
							listLog.add("XX�ظ���" + info);
							continue;
						}

						if (pALLNumber.matcher(info2).matches()) {
							if (isDeleteSamePhone) {
								if (!phoneSetAdd.contains(info2)) {
									phoneSetAdd.add(info2);
									listPhoneAdd.add(info2);
									listLog.add(info);
								} else {
									listLog.add("XX�ظ���" + info);
								}
							} else {
								listPhoneAdd.add(info2);
								listLog.add(info);
							}
							continue;
						}
					}

					isHasNotMach = true;
					listLog.add("XX����ʶ��:" + info);
				}
			}
		} else {
			isHasNotMach = true;
		}
	}

	private void onGetConAll() {
		// TODO Auto-generated method stub
		isLoading = false;
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mpro_.setVisibility(View.GONE);
				StringBuffer sb = new StringBuffer();
				if (isHasNotMach) {
					sb.append("�������ݽ���������鿴XX��Ϣ����\n");
					log_text_view.setTextColor(Color.RED);
					for (String ss : listLog) {
						sb.append(ss).append("\n");
					}
					log_text_view.setText(sb.toString());
					goon_add.setVisibility(View.VISIBLE);

				} else {
					log_text_view.setTextColor(Color.parseColor("#383838"));
					log_text_view.setText("�����������\n��ʼ�洢...");
					saveInto();
				}
			}
		});

	}

	/**
	 * 
	 */
	protected void saveInto() {
		// TODO Auto-generated method stub

		goon_add.setVisibility(View.GONE);

		String mContacts = SharePreferenceutils.getString(mContext,
				ConstantStatic.mContacts, "");

		StringBuffer sb = new StringBuffer();
		for (String ss : listPhoneAdd) {
			sb.append(ss).append(",");
		}

		listPhoneAdd.clear();
		phoneSetAll = phoneSetAdd;
		phoneSetAdd = new HashSet<>();
		listLog.clear();

		SharePreferenceutils.putString(mContext, ConstantStatic.mContacts,
				mContacts + sb.toString());

		log_text_view.setText("�洢���");
	}

	/**
	 * @param e
	 */
	protected void onGetConErr(final Exception e) {
		// TODO Auto-generated method stub
		isLoading = false;
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mpro_.setVisibility(View.GONE);
				showErr(e.getMessage());
			}
		});
	}

	/**
	 * 
	 */
	protected void showErr(String info) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case Dialog.BUTTON_POSITIVE:
					SharePreferenceutils.putString(mContext, ConstantStatic.mContacts, "");
					phoneSetAll.clear();

					showBaseLog();
					break;
				case Dialog.BUTTON_NEGATIVE:

					break;
				// case Dialog.BUTTON_NEUTRAL:
				// Toast.makeText(mContext, "����" + which, Toast.LENGTH_SHORT)
				// .show();
				// break;
				}
			}
		};
		// dialog��������
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // �ȵõ�������
		builder.setTitle("��ʾ"); // ���ñ���
		builder.setMessage("�ļ���������:" + info); // ��������
		// builder.setIcon(R.mipmap.ic_launcher);// ����ͼ�꣬ͼƬid����
		builder.setPositiveButton("ȷ��", dialogOnclicListener);
		// builder.setNegativeButton("ȡ��", dialogOnclicListener);
		// builder.setNeutralButton("����", dialogOnclicListener);
		builder.create().show();

	}

	/**
	 * 
	 */
	private void isDelete() {
		// TODO Auto-generated method stub
		DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case Dialog.BUTTON_POSITIVE:
					SharePreferenceutils.putString(mContext, ConstantStatic.mContacts, "");
					showBaseLog();
					break;
				case Dialog.BUTTON_NEGATIVE:

					break;
				// case Dialog.BUTTON_NEUTRAL:
				// Toast.makeText(mContext, "����" + which, Toast.LENGTH_SHORT)
				// .show();
				// break;
				}
			}
		};
		// dialog��������
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // �ȵõ�������
		builder.setTitle("��ʾ"); // ���ñ���
		builder.setMessage("ɾ��ȫ����"); // ��������
		// builder.setIcon(R.mipmap.ic_launcher);// ����ͼ�꣬ͼƬid����
		builder.setPositiveButton("ȷ��", dialogOnclicListener);
		builder.setNegativeButton("ȡ��", dialogOnclicListener);
		// builder.setNeutralButton("����", dialogOnclicListener);
		builder.create().show();
	}

}
