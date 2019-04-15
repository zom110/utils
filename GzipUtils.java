package com.wodan.platform.foundation.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {


	/**
	 * @Description: 压缩字节数组
	 * @param srcByteArray
	 * @return
	 * @throws IOException
	 */
	public static byte[] gzipByteArray(byte[] srcByteArray) throws IOException{
		byte[] rtByteArray = null;
		if (srcByteArray == null || srcByteArray.length == 0) {
			return rtByteArray;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(srcByteArray);
		gzip.close();
		rtByteArray = out.toByteArray();

		return rtByteArray ;
	}
	

	/**
	 * @Description:解压缩字节数组
	 * @param compressByteArray
	 * @return
	 * @throws IOException
	 */
	public static byte[] gunzipByteArray(byte[] compressByteArray) throws IOException{
		byte[] rtByteArray = null ;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(compressByteArray);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		rtByteArray = out.toByteArray();

		return rtByteArray ;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		String zipHexStr = "1f8b080000000000000babae050043bfa6a302000000";
		byte[] zipBytes = HexUtil.hexStringToBytes(zipHexStr);
		
		byte[] unzipBytes = gunzipByteArray(zipBytes);
		
		System.err.println(new String(unzipBytes));


	}


}
