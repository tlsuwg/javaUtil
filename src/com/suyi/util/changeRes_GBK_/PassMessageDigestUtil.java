package com.suyi.util.changeRes_GBK_;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PassMessageDigestUtil {

	private static final String MD5 = "MD5";
	private static final String SHA1 = "SHA1";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encodeMD5(String str) {
		return encode(MD5, str);
	}

	public static String encodeSHA1(String str) {
		return encode(SHA1, str);
	}
	
	public static String encodeMD5(byte[] str) {
		return encode(MD5, str);
	}


	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		return encode(algorithm,str.getBytes());
	}

	
	public static String encode(String algorithm, byte[] bs) {
		if (bs == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(bs);
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 鎶婂瘑鏂囪浆鎹㈡垚鍗佸叚杩涘埗鐨勫瓧绗︿覆褰㈠紡
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	private static final String HMAC_SHA1 = "HmacSHA1";
	public static String getHmacSHA1Signature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException  {
		byte[] keyBytes = key.getBytes("utf-8");
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data.getBytes("utf-8"));
		return getFormattedText(rawHmac);
	}

}
