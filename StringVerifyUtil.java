package com.wodan.platform.foundation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringVerifyUtil
 * @Description: 字符串验证工具类
 * @author chenw
 * @date 2015-1-14 上午10:17:33
 * @history
 */
public class StringVerifyUtil {

	private static Pattern mobilePhonePattern = Pattern.compile("^(13|14|15|16|17|18|19)\\d{9}$"); // 手机号验证正则
	private static Pattern isNumericPattern = Pattern.compile("[0-9]*"); // 是否是数值

	/**
	 * 验证输入字符串是否是数值
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		Matcher isNum = isNumericPattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * @Description: 手机号验证
	 * @param phoneNum
	 * @return
	 */
	public static boolean VerifyTelePhoneNum(String phoneNum) {
		if (phoneNum == null) {
			return false;
		}
		return mobilePhonePattern.matcher(phoneNum).matches();
	}

	// public static void main(String[] args) {
	// String phoneNum = "13345678901";
	// System.out.println(mobilePhonePattern.matcher(phoneNum).matches());
	// }
}
