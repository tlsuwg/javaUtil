package com.suyi.changeString;
import java.io.IOException;

class test {
	public static final String all = "01234567899";
	
	public static void main(String[] args) throws IOException {
		int i = 0;
		while (true) {
			exchange(i++);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}

	}

	public static void exchange1(int n) {
		int k=n/26;
		StringBuffer sb=new StringBuffer();
		while(k>0){
			sb.append(all.charAt(k % 26));
			k=n/26;
		}
		
		System.out.println(sb.toString());
		
	}
	
	static boolean is0_2;
	public static void exchange(int n) {
//		System.out.println(n);
		if(n==0){
			if(!is0_2){
				is0_2=true;
				System.out.print(all.charAt(n % 26));
			}
		}else{
			is0_2=false;	
		}
		
	
		
		if (n > 0) {
			exchange(n / 26);
			System.out.print(all.charAt(n % 26));
		}
		
	}
}