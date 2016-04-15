package com.suyi.MakeString;

public class MakeMoreString {
	
	public MakeMoreString() {
		// TODO Auto-generated constructor stub
		this("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_");
		
	}

	public MakeMoreString(char[] charSource2) {
		// TODO Auto-generated constructor stub
		init(charSource2);
	}

	private void init(char[] charSource2) {
		// TODO Auto-generated method stub
		this.charSource = charSource2;
		sLength = charSource.length;
	}

	public MakeMoreString(String string) {
		// TODO Auto-generated constructor stub
		byte[] bs = string.getBytes();
		char[] charSource2 = new char[bs.length];
		for (int i = 0; i < bs.length; i++) {
			charSource2[i] = (char) bs[i];
		}
		init(charSource2);
	}

	// 密码可能会包含的字符集合
	char[] charSource;
	int sLength;

	int counter = 0;// 计数器，多线程时可以对其加锁，当然得先转换成Integer类型。
	int mLength = 1;
	int maxLength = 0;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getLength() {
		return mLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	 public static void main(String[] args) {

	 MakeMoreString mMakeMoreString = new MakeMoreString();
	 while (true) {
	 try {
	 mMakeMoreString.getOne();
	 } catch (MaxLengthEx e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
	 }

	private void getOne() throws MaxLengthEx {
		StringBuilder buider = new StringBuilder(mLength * 2);
		int _counter = counter;
		while (_counter >= sLength) {// 10进制转换成26进制
			buider.insert(0, charSource[_counter % sLength]);// 获得低位
			_counter = _counter / sLength;
			_counter--;// 精髓所在，处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
		}
		buider.insert(0, charSource[_counter]);// 最高位
		counter++;
		System.out.println(buider.toString());

		if (buider.toString().length() > mLength) {
			mLength++;
			if (maxLength > 0 && mLength == maxLength) {
				throw new MaxLengthEx();
			}
		}
	}

	public static class MaxLengthEx extends Exception {

	}

}