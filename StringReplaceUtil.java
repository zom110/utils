package com.wodan.platform.foundation.util;

/**
 * 
 * @ClassName: StringReplaceUtil 
 * @Description: 字符串替换工具
 * @author fzdxstf
 * @date 2015-8-27 下午6:19:23 
 * @history 
 *
 */
public class StringReplaceUtil {
	
	
	/**
	 * 去除 空字符和/
	 * @Description:
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str){
		
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				switch (ch) {
					case '/':
					case ' ':
					case '+':
					case '=':
						break;
					default:
						sb.append(ch);
						break;
				}
			}
			
		   return sb.toString(); 
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String sss = "zE/0uW C4TA6I14M3e /1YQAQ==";
		System.out.println(replaceBlank(sss));
		System.out.println(sss.indexOf("d"));

	}
}
