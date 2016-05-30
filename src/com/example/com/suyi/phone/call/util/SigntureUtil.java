package com.example.com.suyi.phone.call.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;

public class SigntureUtil {
	final static String TAG = "Signture";

	private static String[] getPublicKeyString(PackageInfo pi) {
		PublicKey pubKeys[] = getPublicKey1(pi);
		if (pubKeys == null || pubKeys.length == 0) {
			return null;
		}
		String[] strPubKeys = new String[pubKeys.length];
		for (int i = 0; i < pubKeys.length; i++)
			strPubKeys[i] = Base64.encodeToString(pubKeys[i].getEncoded(),
					Base64.DEFAULT);
		return strPubKeys;
	}

	private static PublicKey[] getPublicKey1(PackageInfo pi) {
		try {
			if (pi.signatures == null || pi.signatures.length == 0) {
				return null;
			}
			PublicKey[] publicKeys = new PublicKey[pi.signatures.length];
			for (int i = 0; i < publicKeys.length; i++) {
				byte[] signature = pi.signatures[i].toByteArray();
				
				Su.LogW(i+" "+signature);
				
				CertificateFactory certFactory = CertificateFactory
						.getInstance("X.509");
				InputStream is = new ByteArrayInputStream(signature);
				X509Certificate cert = (X509Certificate) certFactory
						.generateCertificate(is);

				publicKeys[i] = cert.getPublicKey();
				Su.LogW(i+" "+publicKeys[i]);
			}
			return publicKeys;
		} catch (Exception ex) {

		}
		return null;
	}

	public static String[] getInstalledAppPublicKeyString(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			if (pi != null && pi.versionName != null) {
				return getPublicKeyString(pi);
			}
		} catch (NameNotFoundException e) {
			// not installed
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
//
//	public static PublicKey[] getInstalledAppPublicKey(Context context,
//			String packageName) {
//		PackageManager pm = context.getPackageManager();
//		PackageInfo pi;
//		try {
//			pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//			if (pi != null && pi.versionName != null) {
//				return getPublicKey1(pi);
//			}
//		} catch (NameNotFoundException e) {
//			// not installed
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}


//	private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je) {
//		try {
//			// We must read the stream for the JarEntry to retrieve
//			// its certificates.
//			byte[] readBuffer = new byte[1024];
//			InputStream is = jarFile.getInputStream(je);
//			while (is.read(readBuffer, 0, readBuffer.length) != -1)
//				;
//			is.close();
//
//			return (je != null) ? je.getCertificates() : null;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	public static boolean verifySignature(Context context, String packageName,
//			String filePath) {
//		boolean verifyed = true;
//		try {
//			PublicKey[] installedAppPubKeys = getInstalledAppPublicKey(context,
//					packageName);
//			if (installedAppPubKeys == null || installedAppPubKeys.length == 0) {
//				// package not installed
//				return true;
//			}
//			JarFile jarFile = new JarFile(filePath);
//			verifyed = false;
//			JarEntry je = jarFile.getJarEntry("classes.dex");
//			Certificate[] certs = loadCertificates(jarFile, je);
//			if (certs != null && certs.length > 0) {
//				for (int i = 0; i < certs.length; i++) {
//					PublicKey pubKey = certs[i].getPublicKey();
//					for (int j = 0; j < installedAppPubKeys.length; j++) {
//						if (pubKey.equals(installedAppPubKeys[j])) {
//							verifyed = true;
//							break;
//						}
//					}
//					if (verifyed)
//						break;
//				}
//			} else {
//				verifyed = true;
//			}
//
//			jarFile.close();
//		} catch (Exception e) {
//			verifyed = true;
//		}
//
//		return verifyed;
//	}

}
