package com.suyi.util.changeRes_GBK_;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ByteArrayOutputStream indexOut = new ByteArrayOutputStream();
		
		try {
			indexOut.write(new byte[]{1,2,3});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte [] bs=indexOut.toByteArray();
		
		for(byte s:bs){
			System.out.println(s+" ");
		}
		
	}

}
