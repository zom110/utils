package com.wodan.platform.foundation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: PhoneCheckUtil
 * @Description: 联系方式校验
 * @author fzdxstf
 * @date 2015-6-25 下午5:06:11
 * @history
 * 
 */
public class PhoneCheckUtil {

	private static Pattern mobilePhonePattern = Pattern.compile("^^1[3,4,5,7,8][0-9]{9}$"); // 手机号验证正则
	
	/**
	 * 判断联系方式是否为手机号
	 * 
	 * @Description:
	 * @return
	 */
	public static boolean isMobilePhone(String phone) {
		if (phone == null) {
			return false;
		}

		Matcher matcher = mobilePhonePattern.matcher(phone);
		return matcher.matches();

	}

//	public static void main(String[] args) {
//
//		System.out.println(isMobilePhone(null));
//
//	}

}
