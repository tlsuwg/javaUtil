/**
 * 
 */
package com.example.com.suyi.domain;

import java.util.LinkedList;

import android.content.Context;

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

		String allItems = SharePreferenceutils.getString(mContext, ConstantStatic.allItems,
				"");
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

}
