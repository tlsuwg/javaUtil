/**
 * 
 */
package com.example.com.suyi.domain;

/**
 * @author "suwg"
 * 2016��5��17��
 */
public class ContactItem {
	/**
	 * @param info
	 */
	public ContactItem(String phone) {
		// TODO Auto-generated constructor stub
		this.phone=phone;
	}
	public String phone;
	public boolean isSendMessage;
	public boolean isCall;
}
