package com.suyi.changeString;
public class MakeString {

	// String
	// str=
//	String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
	String str = "abcdef";

	char[] chars, lastChars;
	int full;
	int size = 0;

	int changeIndex, changeLast;

	MakeString() {
		chars = str.toCharArray();
		full = chars.length;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MakeString mMakeString = new MakeString();
		
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mMakeString.getOne();
			System.out.println(new String(mMakeString.lastChars));
		}
	}

	char[] getNewOne() {
		System.out.println("getnew one");
		size++;
		lastChars = new char[size];
		changeIndex = 0;
		for (int i = 0; i < size; i++) {
			lastChars[i] = chars[0];
		}
		return lastChars;
	}
	
	boolean isinit;

	private char[] getOne() {
		// TODO Auto-generated method stub
		
		if(!isinit){
			isinit=true;
			return getNewOne();
		}
		

		if (changeIndex == size) {
			return getNewOne();
		} else {
			if (changeLast == full) {
				changeIndex++;
				changeLast=0;
				return getOne();
			} else {
				changeLast++;
				if (changeLast == full) {
					return getOne();
				} else {
					lastChars[changeIndex] = chars[changeLast];
					return lastChars;
				}
			}
		}

	}

}
