package com.qq.reader.zebra.utils;


import com.qq.reader.zebra.cache.core.HexUtil;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * 计算单一文件MD5值
 * @author River
 *
 */
public class MD5Utils {

	public static String getMd5ByFile(File file){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = fis.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			byte[] result = digest.digest();
			StringBuffer sb  = new StringBuffer();
			for (byte b : result) {
				// 与运算
				int number = b & 0xff;
				String str = Integer.toHexString(number);
				if (str.length() == 1) {
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 计算string的MD5
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5ByStr(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return HexUtil.bytes2HexStr(md5.digest(str.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算string的MD5
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return bytes2Hex(md5.digest(str.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 二进制转hex字符串
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 计算string的MD5
	 *
	 * @param str
	 * @return
	 */
	public static String getSHA256ByStr(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("SHA-256");
			return HexUtil.bytes2HexStr(md5.digest(str.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算string的MD5
	 *
	 * @param str
	 * @return
	 */
	public static String getSHA256(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("SHA-256");
			return bytes2Hex(md5.digest(str.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
