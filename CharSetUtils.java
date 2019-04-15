package com.wodan.platform.foundation.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 字符集的工具类
 * 
 * @ClassName: CharSetUtils
 * @author Administrator
 * @date 2015-8-31 下午4:45:39
 * @history
 * 
 */
public class CharSetUtils {
	/**
	 * 纯数字的字符集
	 */
	public static final Set<Character> CHAR_SET_DIGITAL;
	static {
		String tempStr = "1234567890";
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(tempStr.length());
		for (char ch : tempStr.toCharArray()) {
			tempSet.add(Character.valueOf(ch));
		}
		CHAR_SET_DIGITAL = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * 小写字母字符集
	 */
	public static final Set<Character> CHAR_SET_LOWER_CASE;
	static {
		String tempStr = "abcdefghijklmnopqrstuvwxyz";
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(tempStr.length());
		for (char ch : tempStr.toCharArray()) {
			tempSet.add(Character.valueOf(ch));
		}
		CHAR_SET_LOWER_CASE = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * 大写字母字符集
	 */
	public static final Set<Character> CHAR_SET_UPPER_CASE;
	static {
		String tempStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(tempStr.length());
		for (char ch : tempStr.toCharArray()) {
			tempSet.add(Character.valueOf(ch));
		}
		CHAR_SET_UPPER_CASE = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * 大小写字母字符集
	 */
	public static final Set<Character> CHAR_SET_WORD;
	static {
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize("ABCDEFGHIJKLMNOPQRSTUVWXYZ".length() * 2);
		tempSet.addAll(CHAR_SET_UPPER_CASE);
		tempSet.addAll(CHAR_SET_LOWER_CASE);

		CHAR_SET_WORD = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * 字母/数字字符集
	 */
	public static final Set<Character> CHAR_SET_WORD_DIGITAL;
	static {
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(70);
		tempSet.addAll(CHAR_SET_WORD);
		tempSet.addAll(CHAR_SET_DIGITAL);

		CHAR_SET_WORD_DIGITAL = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * uuid字符集
	 */
	public static final Set<Character> CHAR_SET_UUID;
	static {
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(70);
		tempSet.addAll(CHAR_SET_WORD_DIGITAL);
		tempSet.add(Character.valueOf('-'));

		CHAR_SET_UUID = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * base64加密的url字符集
	 */
	public static final Set<Character> CHAR_SET_URL_SAFE_BASE64;
	static {
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(70);
		tempSet.addAll(CHAR_SET_UUID);
		tempSet.add(Character.valueOf('='));

		CHAR_SET_URL_SAFE_BASE64 = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * xml非法字符
	 */
	public static final List<String> XML_FORBIDDEN_CHARS;
	static {
		String str = "<>&'\"";
		List<String> tempSet = Lists.newArrayListWithCapacity(str.length());
		for (char ch : str.toCharArray()) {
			tempSet.add(String.valueOf(ch));
		}

		XML_FORBIDDEN_CHARS = Collections.unmodifiableList(tempSet);
	}
	
	public static final Set<Character> CHAR_SET_HEX;
	static {
		String str = "1234567890abcdefABCDEF";
		Set<Character> tempSet = Sets.newHashSetWithExpectedSize(str.length());
		for (char ch : str.toCharArray()) {
			tempSet.add(Character.valueOf(ch));
		}

		CHAR_SET_HEX = Collections.unmodifiableSet(tempSet);
	}

}
