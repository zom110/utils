package com.wodan.platform.foundation.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	
	
	private static int defaultBufferSize = 1024 ;

	
	/**
	 * 
	 * @Description: 从输入流读取字节数组 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readByteArray(InputStream inputStream ) throws IOException {
		byte[] readedByteArray = null ;
		if(inputStream == null ){
			return readedByteArray ;
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Integer bufferSize = defaultBufferSize ;
		byte[] buffer = new byte[bufferSize];
		int bytesRead = inputStream.read(buffer);
		while (bytesRead != -1) {
			byteArrayOutputStream.write(buffer, 0, bytesRead);
			bytesRead = inputStream.read(buffer);
		}
		readedByteArray = byteArrayOutputStream.toByteArray();

		return readedByteArray ;
	}
			
			

}
