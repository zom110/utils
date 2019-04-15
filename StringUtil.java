package com.wodan.platform.foundation.util;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * StringUtil
 *
 * @author longlin(longlin@cyou-inc.com)
 * @date 2013-11-6
 * @since V1.0
 */
public class StringUtil extends StringUtils {
    /**
     * 是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return (s == null || "".equals(s.trim()));
    }

    /**
     * 是否不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 是否包含中文
     *
     * @param s
     * @return
     */
    public static boolean isContainZH(String s) {
        return isEmpty(s) || Pattern.compile("[\\u4e00-\\u9fa5]").matcher(s).find();
    }

    public static String trim(String s) {
        return s != null ? s.trim() : null;
    }
    /**
     * 删除换行符 \r
     * @Description:
     * @param s
     * @return
     */
	public static String removeReturnSym(String s){
		
		String last = s.substring(s.length()-1);
		if("\r".equals(last)){
			s = s.substring(0, s.length()-1);
		}
		return s;
	}

	
    /**
     * 字符串转换为长整形的set
     * @Description:
     * @param str
     * @param splitter
     * @return
     */
    public static Set<Long> str2LongSet(String str, String splitter) {
    	Assert.notNull(str);
    	Assert.hasText(splitter);
    	
    	final String[] split = str.split(splitter);
		Set<Long> resultSet = Sets.newHashSet();
		for (String temp : split) {
			resultSet.add(Long.valueOf(temp));
		}

		return resultSet;
    }
    
    /**
     * 字符串转换为长整形的list
     * @Description:
     * @param str
     * @param splitter
     * @return
     */
    public static List<Long> str2LongList(String str, String splitter) {
    	Assert.notNull(str);
    	Assert.hasText(splitter);
    	
    	final String[] split = str.split(splitter);
		List<Long> resultSet = Lists.newArrayList();
		for (String temp : split) {
			resultSet.add(Long.valueOf(temp));
		}

		return resultSet;
    }

	public static Set<Integer> str2IntegerSet(String str, String splitter) {
		Assert.notNull(str);
    	Assert.hasText(splitter);
    	
    	final String[] split = str.split(splitter);
		Set<Integer> resultSet = Sets.newHashSet();
		for (String temp : split) {
			resultSet.add(Integer.valueOf(temp));
		}

		return resultSet;
	}
}
