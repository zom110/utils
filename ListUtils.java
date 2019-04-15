package com.wodan.platform.foundation.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * list工具
 * 
 * @ClassName: ListUtils
 * @author Administrator
 * @date 2015-1-16 下午2:59:30
 * @history
 * 
 */
public class ListUtils {
	/**
	 * 获取第一个
	 * 
	 * @Description:
	 * @param list
	 * @return
	 */
	public static <T> T getFirst(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	public static List<String> longList2String(List<Long> longList) {
		if (CollectionUtils.isEmpty(longList)) {
			return null;
		}

		List<String> resultList = new ArrayList<String>();
		for (Long l : longList) {
			resultList.add(String.valueOf(l));
		}
		return resultList;
	}

	public static <T> List<T> createSubList(List<T> list, RowBounds rowBounds) {
		Assert.notNull(rowBounds);
		
		if(CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		
		int beginIndex = Math.min(rowBounds.getOffset(), list.size());
		int endIndex = Math.min(rowBounds.getOffset() + rowBounds.getLimit(), list.size());
		return list.subList(beginIndex, endIndex);
	}
}
