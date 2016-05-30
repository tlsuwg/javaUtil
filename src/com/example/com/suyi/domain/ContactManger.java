/**
 * 
 */
package com.example.com.suyi.domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.example.com.suyi.phone.call.R;
import com.example.com.suyi.phone.call.util.SharePreferenceutils;
import com.example.com.suyi.phone.call.util.StringUtile;

/**
 * @author "suwg" 2016年5月17日
 */
public class ContactManger {

	LinkedList<ContactItem> listNoCall = new LinkedList<>();
	LinkedList<ContactItem> listHasCall = new LinkedList<>();
	Context mContext;

	/**
	 * @param mContext
	 */
	public ContactManger(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	public ContactItem getNextOne() {
		return listNoCall.remove(0);
	}

	public void hasDoCall(ContactItem mContactItem) {
		listHasCall.add(mContactItem);
	}

	StringBuffer sbAll;

	public int getNocallSize() {
		return listHasCall.size();
	}

	public int gethasCallSize() {
		return listHasCall.size();
	}

	public String info() {
		if (sbAll == null) {
			sbAll = new StringBuffer();
			sbAll.append("all:").append(listNoCall.size()).append("\n");
		}

		StringBuffer sb = new StringBuffer();
		sb.append(sbAll.toString()).append("now left:")
				.append(listNoCall.size());

		return sb.toString();
	}

	public void getAllItems() throws Exception {

		String allItems = SharePreferenceutils.getString(mContext,
				ConstantStatic.allItems, "");
		if (StringUtile.isEmpty(allItems))
			throw new Exception("没有发现联系人数据包，请重现导入联系人");

		if (!allItems.contains(","))
			throw new Exception("没有发现联系人数据包，请重现导入联系人");

		String infos[] = allItems.split(",");

		for (String info : infos) {
			if (StringUtile.isEmpty(info))
				continue;
			listNoCall.add(new ContactItem(info));
		}

		if (listNoCall.size() == 0) {
			throw new Exception("数据包为空，请导入联系人");
		}

	}

	public void getPhoneAllContact() throws Exception {
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		ContentResolver resolver = mContext.getContentResolver();
		Cursor cursor = resolver.query(uri, new String[] { "_id" }, null, null,
				null);
		while (cursor.moveToNext()) {
			int contractID = cursor.getInt(0);
			StringBuilder sb = new StringBuilder("contractID=");
			sb.append(contractID);
			uri = Uri.parse("content://com.android.contacts/contacts/"
					+ contractID + "/data");
			Cursor cursor1 = resolver.query(uri, new String[] { "mimetype",
					"data1", "data2" }, null, null, null);
			while (cursor1.moveToNext()) {
				String data1 = cursor1.getString(cursor1
						.getColumnIndex("data1"));
				String mimeType = cursor1.getString(cursor1
						.getColumnIndex("mimetype"));
				if ("vnd.android.cursor.item/name".equals(mimeType)) { // 是姓名
					sb.append(",name=" + data1);
				} else if ("vnd.android.cursor.item/email_v2".equals(mimeType)) { // 邮箱
					sb.append(",email=" + data1);
				} else if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) { // 手机
					sb.append(",phone=" + data1);
				}
			}
			cursor1.close();
		}
		cursor.close();
	}
	
	public String getPhoneLocalContactsString(){
		 List<ContactsInfo> list=getPhoneLocalContactsInfos();
		 if(list==null)return null;
		 StringBuffer sb=new StringBuffer();
		 sb.append(list.size());
		 
		 for(ContactsInfo it:list){
			 sb.append(it.name).append(":").append(it.contactsPhone).append(";");
		 }
		 return sb.toString();
	}

	public List<ContactsInfo> getPhoneLocalContactsInfos() {
		List<ContactsInfo> localList = new ArrayList<ContactsInfo>();
		ContentResolver cr = mContext.getContentResolver();
		String str[] = { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
				Phone.PHOTO_ID };
		Cursor cur = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
				null, null);
		ContactsInfo contactsInfo = null;
		if (cur != null) {
			while (cur.moveToNext()) {
				contactsInfo = new ContactsInfo();
				contactsInfo.setContactsPhone(cur.getString(cur
						.getColumnIndex(Phone.NUMBER)));// 得到手机号码
				contactsInfo.setContactsName(cur.getString(cur
						.getColumnIndex(Phone.DISPLAY_NAME)));
				// contactsInfo.setContactsPhotoId(cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID)));
				// long contactid = cur.getLong(cur
				// .getColumnIndex(Phone.CONTACT_ID));
				// long photoid =
				// cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID));
				// 如果photoid 大于0 表示联系人有头像 ，如果没有给此人设置头像则给他一个默认的
				// if (photoid > 0) {
				// Uri uri = ContentUris.withAppendedId(
				// ContactsContract.Contacts.CONTENT_URI, contactid);
				// InputStream input = ContactsContract.Contacts
				// .openContactPhotoInputStream(cr, uri);
				// contactsInfo.setBitmap(BitmapFactory.decodeStream(input));
				// } else {
				// contactsInfo.setBitmap(BitmapFactory.decodeResource(
				// mContext.getResources(), R.drawable.ic_launcher));
				// }

				System.out.println("---------联系人电话--"
						+ contactsInfo.contactsPhone);
				localList.add(contactsInfo);

			}
		}
		cur.close();
		return localList;

	}

	public static class ContactsInfo {

		public String contactsPhone, name;

		/**
		 * @param string
		 */
		public void setContactsPhone(String contactsPhone) {
			// TODO Auto-generated method stub
			this.contactsPhone = contactsPhone;

		}

		/**
		 * @param string
		 */
		public void setContactsName(String name) {
			// TODO Auto-generated method stub
			this.name = name;
		}

	}

}
