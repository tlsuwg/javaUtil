package com.suyi.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wif {
	
	
//	 public static void a(byte[] paramArrayOfByte)
//	  {
//	    int i = 0;
//	    int j = -2 + paramArrayOfByte.length;
//	    int k;
//	    if (paramArrayOfByte[j] == -86)
//	    {
//	      k = 0;
//	      if (k >= j)
//	        paramArrayOfByte[(j + 1)] = i;
//	    }
//	    else
//	    {
//	      return;
//	    }
//	    if ((k & 0x1) == 1);
//	    for (i = (byte)(i ^ paramArrayOfByte[k]); ; i = (byte)(i + paramArrayOfByte[k]))
//	    {
//	      k++;
//	      break;
//	    }
//	  }
//	 
	 
	 
//	 public static void a(byte[] paramArrayOfByte){
//		 
//		 int i=0;
//		 for(int k=0;k<=paramArrayOfByte.length;k++){
//			 i+=paramArrayOfByte[k];
//			 i=i^ paramArrayOfByte[k];
//		 }
//		 
//	 }

	public static void main(String[] args) throws Exception {

		// File f = new File("C:/Users/suwg/Desktop/MTK/WIFI000000000001");
		// File f = new File("C:/Users/suwg/Desktop/MTK/WIFI000000000000");/
		// File f = new File("C:/Users/suwg/Desktop/MTK/WIFIffffffffffff");
		File f = new File("C:/Users/suwg/Desktop/MTK/WIFI32df962c3961");

		InputStream input = null;
		input = new FileInputStream(f);

		byte bs[] = new byte[(int) f.length()];
		int len = input.read(bs);

		input.close();
		System.out.println("读入数据的长度：" + len);
		System.out.println("内容为：" + new String(bs, "UTF-8"));
		System.out.println("WIFI32df962c3961");

		int times = 6;

		Map map = new HashMap();

		// 4G LTE,MT6575,1460,3812,20000,2A0042,300000
		// AMOI N828,MT6589,35C0,8036,20000,2A0042,500000
		// JY-G2,MT6577,29B4,80F6,20000,4A0072,500000
		// Lenovo A366t,MT6573,2068,49FC,40000,24001E,300000
		// Lenovo A60,MT6573,29B4,7F34,21000,2B5042,300000
		// Lenovo A65,MT6573,2A40,7F58,21000,2B5042,300000
		// Lenovo A60+,MT6575,29B4,8018,40000,24001E,300000
		// Lenovo A60+,MT6575,29B4,8018,42000,0,300000
		// Lenovo A66,MT6575,2928,7FA8,40000,24001E,300000
		// Lenovo A690,MT6575,29B4,8018,40000,24001E,300000
		// Lenovo A750,MT6575,29B4,80AC,20000,2A0042,300000
		// Lenovo A630t,MT6577,20F4,4C9A,20000,4A0072,500000
		// Lenovo A789,MT6577,2A40,8074,20000,4A0072,500000
		// Lenovo A798t,MT6577,2068,4C94,20000,4A0072,500000
		// Lenovo A800,MT6577, 2A40,80BE,20000,4A0072,500000
		// Lenovo A820,MT6589, 387C, 861A, 20000, 2A0042,500000
		// Lenovo A820t,MT6589, 1E3C,3D1E,20000,2A0042,500000
		// ZTE U795,MT6575, 1FDC,4ABC,20000,2A0042,300000
		// ZTE U795+,MT6577, 1FDC,4C3C,20000,4A0042,500000
		// ZTE U807,MT6577, 1FDC,4C1C,20000,4A0042,500000
		// ZTE V818,MT6572, 3DF4,89BC,20000,440069,500000
		// ZTE V889S,MT6577, 2928,7FF2,20000,4A0072,500000
		// ZTE V967S,MT6589, 37F0,85BE,20000,2A0042,500000
		// ZTE V970,MT6577, 2928,7FF2,20000,4A0072,500000
		// ZTE V987,MT6589, 37F0,85BE,20000,2A0042,500000
		
		
		String a32too = "4,1,0,0, 50,-33,-106,44,57,97, 0,0,33,33,0,32,32,32,32,32,32,32,32,32,32,32,32,30,30,30,30,30,30,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-72,115,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,38,30,26,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-86,89,1";


		String a001 = "4,1,0,0,  0, 0, 0, 0, 0, 1, 0,0,33,33,0,30,30,30,30,30,30,0,0,0,0,-72,115,15,0,0,0,0,1,0,0,38,30,26,0,1,0,0,0,0,-86,-94,1";
		String a000 = "4,1,0,0,  0, 0, 0, 0, 0, 0, 0,0,33,33,0,30,30,30,30,30,30,0,0,0,0,-72,115,15,0,0,0,0,1,0,0,38,30,26,0,1,0,0,0,0,-86,29,1";
		String afff = "4,1,0,0, -1,-1,-1,-1,-1,-1, 0,0,33,33,0,30,30,30,30,30,30,0,0,0,0,-72,115,15,0,0,0,0,1,0,0,38,30,26,0,1,0,0,0,0,-86,-69,1";
		String a32 = "4,1,0,0, 50,-33,-106,44,57,97, 0,0,33,33,0,30,30,30,30,30,30,0,0,0,0,-72,115,15,0,0,0,0,1,0,0,38,30,26,0,1,0,0,0,0,-86,-56,1";

		String argss[] = new String[] { a001, a000, afff, a32 };

		// HashMap maps=new HashMap();
		List<String[]> list = new ArrayList();
		
//		WIFI32df962c3961
		

		for (String ss : argss) {
			String[] all = ss.split(",");
			list.add(all);
		}
		
		String[] sss=list.get(3);
		
		
		int all=0;
		for (int i = 4; i < 10; i++) {
			int k=Integer.parseInt(sss[i].trim());
//			System.out.println(k);
			System.out.println(Integer.toHexString(k));
			all+=k;
		}
		
		System.out.println("cai"+all);
		
		System.out.println("true"+sss[sss.length-2]);
		
		
//		String[] all222 = a32too.split(",");
//		
//		byte bsss[]=new byte[all222.length];
//		
//		System.out.println(bsss.length);
//		
//		for (int i = 0; i < all222.length; i++) {
//			int k=Integer.parseInt(all222[i].trim());
////			System.out.println(k);
//			System.out.println(Integer.toHexString(k));
//			bsss[i]=(byte) k;
//		}
//		
//		
//		File f3 = new File("C:/Users/suwg/Desktop/MTK/WIFI32df962c3961333");
//	
//		FileOutputStream output=new FileOutputStream(f3);
//
//		  output.write(bsss);
//		  output.flush();
//		 
		  


//		for (int i = 0; i < list.get(0).length; i++) {
//			boolean is = false;
//			String old = null;
//			for (String[] sts : list) {
//				if (old != null) {
//					is = sts[i].equals(old);
//					if (!is) {
//						break;
//					}
//				}
//				old = sts[i];
//			}
//			if (!is) {
//				System.out.println(i);
//			}
//		}

		// 1－8字节为串1密文；
		// 9－10字节为补充码；
		// 11－12字节为串1验证码；
		// 13－24字节为串2加密码，各字节意义同串1；
		// 25－120字节意义不明，因与串号暂无关连不用理会。

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// {
		// for (byte s : bs) {
		// System.out.print(s + ",");
		// }
		// System.out.println("1");
		//
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// 32 35 33 31 32 39 36 36 38 34 34 33 30 32 32 00 253129668443022.

		// byte[] imeiChars="253129668443022".getBytes();
		// int[] bytes=new int[]{ 0xAB, 0xA0 , 0x6F , 0x2F , 0x1F , 0x1E , 0x9A
		// , 0x45 };

		// String key=a1NStr;
		// byte[] imeiChars=key.getBytes();
		// byte[] bytes=a1Nbs;

		// String key=a119Str;
		// byte[] imeiChars=key.getBytes();
		// byte[] bytes=a119bs;

		// String key=a2NStr;
		// byte[] imeiChars=a2NStr.getBytes();
		// byte[] bytes=a2Nbs;

		// 交换

		// int changeInt[]=new int[imeiChars.length];

		// 358142039424047 358142031011461 mi==
		// 0xffffffab,0xffffffa0,0x6f,0x2f,0x1f,0x1e,0xffffff9a,

		// 358142034891794 mi==
		// 0xffffffab,0xffffffa0,0x6f,0x2f,0xffffff1f,0x1e,0x9a,

		// 111111111111119 mi==
		// 0xffffffab,0xffffffa0,0x6f,0x2f,0x1f,0x1e,0xffffff9a,
		//

		// 211111111111118 mi==
		// 0xffffffab,0xffffffa0,0x6f,0x2f,0x1f,0x1e,0xffffff9a,

		// // byte[] Keybytes="1234567".getBytes();
		// byte[] Keybytes=new byte[7];
		//
		// for(int i=0;i<Keybytes.length;i++){
		// Keybytes[i]=(byte)(i);
		// }
		//
		//
		// int jianqu=30;
		//
		// for(int i=0; i<14; i+=2)
		// {
		//
		// // if(imeiChars[i] <= 0x2F && imeiChars[i] > 0x39) break; //非数字
		// // if(imeiChars[i+1] <= 0x2F && imeiChars[i+1] > 0x39) break;
		//
		// int b=(imeiChars[i]) + ((imeiChars[i+1])<< 4);
		//
		// // System.out.println(b);
		// // System.out.println((b & 0xff)+"  "+Integer.toHexString(b));
		//
		// Keybytes[i/2] = (byte) ( b ^ i);
		//
		// // Keybytes[i/2] =(byte)b;
		//
		// System.out.println( Keybytes[i/2]+ "  " +(Keybytes[i/2] & 0xff)+"  "
		// +Integer.toHexString(Keybytes[i/2] & 0xff));
		// }
		//
		// System.out.println(0xAB ^ 0x52);

		// 从15位"253129668443022" 转换成7byte[]
		//
		// 1 获取到14的数字值 ( 第一位就是2 第二位是5 。。。。。。)
		// 2 获取相加 第一次 就是 a= 2+ (5<<4)
		// 3第一位byte b= (相加数字a) ^ ? (此处是什么呢 0 1 2 3 4 5 6 7？)
		//

		// System.out.println(ss);
		// Key[7] = (byte) (chars[14]-30) ^ Key[0/2];

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// for (int i = a000bs.length - 1; i >= 0; i--) {
		//
		// System.out.println((char)a2118bs[i]);
		//
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//
		// for (int i = a000bs.length - 1; i >= 0; i--) {
		//
		// System.out.println(byteto2String(a119bs[i]));
		//
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// // 查找变化
		// for (int i = a000bs.length - 1; i >= 0; i--) {
		//
		// if (
		// // a000bs[i] == a119bs[i]
		// // &&
		// a119bs[i] == a2118bs[i]
		// // &&
		// // a2118bs[i] == a1Nbs[i]
		// // &&
		// // a1Nbs[i] == a2Nbs[i]
		// ) {
		// // System.out.println();
		// } else {
		// System.out.println("" + i+"  "+(a119bs[i] & 0xff)+" : "+(a2118bs[i]&
		// 0xff));
		// }
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// 大小端变化ytee

		// byte[] paramArrayOfByte = new byte[4];
		//
		// for (int i = 0; i < paramArrayOfByte.length; i++) {
		//
		// byte bbbbb= (byte) (a1Nbs[i]);
		// System.out.println();
		// System.out.println(bbbbb);
		//
		// // String changeString12 = printHexString(bbbbb);
		// // System.out.println(changeString12);
		// // String chenged = changeString12(changeString12);
		// // System.out.println(chenged);
		// // int kk = Integer.parseInt(chenged, 16);
		// // System.out.println(kk);
		// // byte bb = (byte) kk;
		// // System.out.println(bb);
		// paramArrayOfByte[i] = bbbbb;
		// }
		// System.out.println();
		//
		// System.out.println(getIntByBytes(paramArrayOfByte, 0));

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// System.out.println("");
		// System.out.println((short)a119bs[8]);
		// System.out.println(byte2bits(a119bs[8]));
		// System.out.println((short)a119bs[9]);
		// System.out.println(byte2bits(a119bs[9]));
		// System.out.println();
		// System.out.println(a119bs[12]);
		// System.out.println(byte2bits(a119bs[12]));
		// System.out.println(a119bs[13]);
		// System.out.println(byte2bits(a119bs[13]));
		// System.out.println(a119bs[14]);
		// System.out.println(byte2bits(a119bs[14]));
		// System.out.println(a119bs[15]);
		// System.out.println(byte2bits(a119bs[15]));
		// System.out.println();
		// System.out.println(byte2bits(a119bs[20]));
		// System.out.println(byte2bits(a119bs[21]));
		//
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// for (int i = 24 - 1; i >= 0; i--) {
		//
		// System.out.println(0xFF &a1Nbs[i] );
		//
		// // System.out.println(byte2bits(a119bs[i]));
		//
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// for (int i = 0; i < 24; i++) {
		// System.out.println(0xFF &a1Nbs[i] );
		// // System.out.println(byte2bits(a119bs[i]));
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// byte[] bynow = new byte[4];
		// int i=0;
		// for (int k = 4-1; k >= 0; k--) {
		// byte b = a2Nbs[k];
		// bynow[i] = b;
		// i++;
		// }
		//
		// System.out.print(getIntByBytes(bynow,0) + "倒叙 ");
		//
		// i=0;
		// for (int k =0; k <4; k++) {
		// byte b = a2Nbs[ k];
		// bynow[i] = b;
		// i++;
		// }
		//
		// System.out.print(getIntByBytes(bynow,0) + "正序 ");
		//

		// for (int i = 18; i > 0; i-=4) {
		//
		// for (int k = 0; k < 4; k++) {
		// bynow[k] = a119bs[i-k-1];
		// }
		//
		// System.out.print(getIntByBytes(bynow, 0) + " ");
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// for (int i = 0; i < 4; i++) {
		// byte[] bynow = new byte[6];
		// for (int k = 0; k < 6; k++) {
		// byte b = a000bs[i * times + k];
		// bynow[k] = b;
		// System.out.print(byte2bits(b) + " ");
		// }
		// System.out.println();
		// }

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//
		// for(int i=len/times;i>0;i--){
		//
		// String chat=ingt.charAt(i-1)+"";
		//
		// byte[] bynow=new byte[times];
		// String byts8Str=new String();
		//
		// // System.out.println("now :"+chat);
		//
		//
		// for(int k=0;k<times;k++){
		//
		// byte b=bs[i*times-k-1];
		// // System.out.print(bynow[k]+",");
		//
		// bynow[k]=b;
		//
		// System.out.print(byte2bits(b)+" ");
		//
		// byts8Str+=byte2bits(b);
		//
		// }
		//
		//
		// // if(map.containsKey(byts8Str)){
		// //
		// // System.out.println(chat +"  jiude: "+map.get(byts8Str));
		// //
		// // }else{
		// // map.put(byts8Str, chat);
		// // }
		// //
		// //
		//
		//
		// // int thisByteInt=getByBytes(bynow,0,0);
		//
		// System.out.println("");
		//
		// // System.out.println(" ? == " +ingt.charAt(i)+ " 我的：" +thisByteInt);
		//
		// }
		//

	}

	private static byte[] get_Mp001Bytes_ByMi_IMEIString(String imeiString,
			int[] mi, byte[] a2118bs) {
		byte[] imeiStrBytes = imeiString.getBytes();
		int ints[] = new int[7];
		for (int i = 0; i < 14; i += 2) {
			int s1 = Integer.parseInt((char) imeiStrBytes[i] + "");
			int s2 = Integer.parseInt((char) imeiStrBytes[i + 1] + "");
			int kk = s2 * 16 + s1;
			ints[i / 2] = kk;
		}
		byte[] mpBytes = new byte[7];
		for (int i = 0; i < 7; i++) {
			mpBytes[i] = (byte) (mi[i] ^ ints[i]);
			System.out.println(mpBytes[i]);
			System.out.println(a2118bs[i]);
			// System.out.print( "1110x"+ Integer.toHexString(mpBytes[i] )+
			// ",");
		}
		return mpBytes;
	}

	private static String get_IMEIString_ByMi_Mp001Bytes(int[] mi,
			byte[] mp001Bytes) {
		String imeiString14 = "";
		for (int i = 0; i < 7; i++) {
			int kk = mi[i] ^ mp001Bytes[i];
			String ss = Integer.toHexString(kk);
			ss = ss.charAt(1) + "" + ss.charAt(0);
			imeiString14 += ss;
		}
		String str15 = getImei15(imeiString14);
		return str15;
	}

	private static int[] get_MI_ByIMEIString_Mp001Bytes(String imeiStr,
			byte[] mp001Bytes) {
		byte[] imeiStrBytes = imeiStr.getBytes();
		int ints[] = new int[7];
		for (int i = 0; i < 14; i += 2) {
			int s1 = Integer.parseInt((char) imeiStrBytes[i] + "");
			int s2 = Integer.parseInt((char) imeiStrBytes[i + 1] + "");
			int kk = s2 * 16 + s1;
			ints[i / 2] = kk;
		}
		int[] miwen = new int[7];
		for (int i = 0; i < 7; i++) {
			miwen[i] = mp001Bytes[i] ^ ints[i];
			System.out.print("0x" + Integer.toHexString(miwen[i]) + ",");
		}
		return miwen;
	}

	public static String byteto2String(byte b) {
		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	public static String changeString12(String printHexString) {
		String hex = printHexString.charAt(1) + "" + printHexString.charAt(0);
		return hex;
	}

	// cheng 16
	public static String printHexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex;
	}

	private static int getIntByBytes(byte[] paramArrayOfByte, int offset) {
		// TODO Auto-generated method stub

		int value;
		// value = (byte) (((src[offset ] & 0xFF) << 24)
		// | ((src[offset +1 ] & 0xFF) << 16)
		// | ((src[offset + 2] & 0xFF) << 8)
		// | (src[offset + 3] & 0xFF));

		value = (int) (0xFF & paramArrayOfByte[0]
				| (0xFF & paramArrayOfByte[1]) << 8
				| (0xFF & paramArrayOfByte[2]) << 16 | (0xFF & paramArrayOfByte[3]) << 24);
		return value;

	}

	/**
	 * imei由15位数字组成， 前6位(TAC)是型号核准号码，代表手机类型。 接着2位(FAC)是最后装配号，代表产地。
	 * 后6位(SNR)是串号，代表生产顺序号。 最后1位 (SP)是检验码。
	 * 
	 * 检验码计算： (1).将偶数位数字分别乘以2，如果是2位数 ，计算个位数和十位数之和，然后整体求和 (2).将奇数位数字相加，再加上上一步算得的值
	 * (3).如果得出的数个位是0则校验位为0，否则为10减去个位数
	 * 
	 * @author sonzer
	 * 
	 */

	public static String getImei15(String imeiString14) {

		char[] imeiChar = imeiString14.toCharArray();
		int resultInt = 0;
		for (int i = imeiChar.length - 1; i >= 0; i--) {
			int a = Integer.parseInt(String.valueOf(imeiChar[i]));
			if (i % 2 == 1) {
				int temp = a * 2;
				if (temp > 9) {
					String shi = temp + "";
					resultInt += (Integer.parseInt(shi.charAt(0) + "") + Integer
							.parseInt(shi.charAt(1) + ""));
				} else {
					resultInt += temp;
				}
			} else {
				resultInt += a;
			}
		}
		int kk = resultInt = 10 - (resultInt % 10);

		System.out.println("imei:" + imeiString14 + "  " + resultInt);

		return kk + "";

	}

}