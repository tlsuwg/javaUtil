package com.suyi.android.util.makeSecretFile;

public class SuAPKException extends Exception {

	public SuAPKException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

	public SuAPKException() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public SuAPKException(String message, Throwable cause,
//			boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//		// TODO Auto-generated constructor stub
//	}

	public SuAPKException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SuAPKException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "SuAPKException##"+super.getMessage();
	}
	
	

}
