package com.wodan.platform.foundation.util;

import java.util.Collection;
import java.util.List;

import org.springframework.util.Assert;

/**
 * @ClassName: ArrayUtils
 * @Description: 数组工具类
 * @author chenw
 * @date 2015-1-23 下午5:42:58
 * @history
 */
public class ArrayUtils {

	/**
	 * 字符串转换long
	 * 
	 * @Description:
	 * @param str
	 * @return
	 */
	public static Long[] strToLong(String[] str) {
		Assert.notNull(str);

		Long[] resultArray = new Long[str.length];
		for (int i = 0; i < str.length; i++) {
			resultArray[i] = Long.valueOf(str[i]);
		}

		return resultArray;
	}

	public static <T> T[] toArray(Class<T> clazz, Collection<T> collection, T otherString) {
		int totalSize = collection.size() + 1;

		ArrayBuilder<T> builder = new ArrayBuilder<T>(clazz, totalSize);
		for (T element : collection) {
			builder.append(element);
		}
		builder.append(otherString);

		return builder.build();
	}

	public static <T> T[] toArray(Class<T> clazz, List<Collection<T>> collections) {
		int totalSize = 0;
		for (Collection<T> coll : collections) {
			totalSize += coll.size();
		}

		ArrayBuilder<T> builder = new ArrayBuilder<T>(clazz, totalSize);
		for (Collection<T> collection : collections) {
			for (T element : collection) {
				builder.append(element);
			}
		}

		return builder.build();
	}
}
