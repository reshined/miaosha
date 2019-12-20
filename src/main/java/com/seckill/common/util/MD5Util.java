package com.seckill.common.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	private static  final String salt  = "1a2b3c4d";


	public static String md5(String src){
		return DigestUtils.md5Hex(src);
	}
	//第一次加密，通过固定的salt
	public static String inputPassFormPass(String inputPass){
		String str =""+ salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}
	//第二次加密
	public static String formPassToDBpass(String formPass,String salt){
		String str =""+ salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}
	//注册加密方法
	public static String inputPassToDbPass(String inputPass,String saltDB){
		String formPass = inputPassFormPass(inputPass);
		String dbPass   = formPassToDBpass(formPass,saltDB);
		return dbPass;
	}

	public static void main(String[] args) {

		System.out.println(inputPassFormPass("000000"));
	}
}
