package com.wodan.platform.foundation.util;

import java.nio.ByteBuffer;

/**
 * @ClassName: ByteUtil
 * @Description: 字节处理工具类
 * @author chenw
 * @date 2014-12-26 下午5:22:28
 * @history
 * 
 */
public class ByteUtil {

	/**
	 * 将Integer转换为byte[]
	 * 
	 * @Description:
	 * @param l
	 * @param defaultValue
	 * @return
	 */
	public static byte[] intObj2Bytes(Integer l, byte[] defaultValue) {
		if (l == null) {
			return defaultValue;
		}

		return int2Bytes(l.intValue());
	}

	/**
	 * int2Bytes
	 * 
	 * @Description:
	 * @param i
	 * @return
	 */
	public static byte[] int2Bytes(int i) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(i);
		return buffer.array();
	}
}
