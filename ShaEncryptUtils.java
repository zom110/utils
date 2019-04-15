package com.wodan.platform.foundation.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaEncryptUtils {
	
	
	/**
	 * 
	 * @Description: sha1 签名
	 * @param sourceByteArray
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] sha1Encrypt(byte[] sourceByteArray) throws NoSuchAlgorithmException {
		byte[] rtByteArray = null ;

		if(sourceByteArray == null ){
			return rtByteArray ;
		}

		MessageDigest md = MessageDigest.getInstance("SHA1");
		rtByteArray = md.digest(sourceByteArray);

		return rtByteArray ;
	}


	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String sourceStr = "aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国aaaa中文bbbb中国";
		String charset = "UTF-8";

		// 加密 
		byte[] sourceByteArray = sourceStr.getBytes(charset);
		byte[] signByteArray = sha1Encrypt(sourceByteArray); 
		System.err.println("签名前内容:" + sourceStr );
		System.err.println("签名内容长度:" + sourceByteArray.length );
		System.err.println("签名内容:" + new String(signByteArray,charset)  );		
		System.err.println("签名内容长度:" + signByteArray.length );
		
		


	}

}
