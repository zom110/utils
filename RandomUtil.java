package com.wodan.platform.foundation.util;

import java.util.Random;

/**
 *@ClassName:RandomUtil
 *@Description:
 *@author  zhangls
 *@date 2015-1-8  下午8:22:12
 *@history
 */
public class RandomUtil {
	/**
	 * 随机数种子
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	
	/**
	 * 验证码
	 * */
	private static char[] identifyingCode=("0123456789").toCharArray();
	
	
	/**
	 * @Description:生成16位随机数
	 * @return randBuffer
	 */
	public static String random(int len){
		Random random = new Random();
		char[] randBuffer = new char[len];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[random.nextInt(len)];
		}
		return new String(randBuffer);
	}
	
	
	/**
	 * 获取6位验证码
	 * */
	public static String identifyingCodeRandom(){
		Random random = new Random();
		char[] randBuffer = new char[6];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = identifyingCode[random.nextInt(6)];
		}
		return new String(randBuffer);
	}
	
	
	/**
	 * 获取账户随机密码
	 */
	public static String getRandomPwd(int len){
		String base = "23456789abcdefghijkmnpqrstuvwxyz23456789";     
	    Random random = new Random();     
	    StringBuilder sb = new StringBuilder();     
	    for (int i = 0; i < len; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString(); 
	}
	
	/**
	 * 获取后台账户四位识别码
	 */
	public static String getUserInfoRandomPwd(int len){
		String base = "123456789";     
	    Random random = new Random();     
	    StringBuilder sb = new StringBuilder();     
	    for (int i = 0; i < len; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString(); 
	}
	
	
	//生成随机数
	public static String getRandomNum(int len){
		String randomNum ;
		Random random = new Random();     
		while(true){
			int i=random.nextInt(99999999);
			if(i<0)i=-i;
			randomNum = String.valueOf(i);
			if(randomNum.length()<len){
				continue;
			}
			if(randomNum.length()>=len){
				randomNum = randomNum.substring(0,len);
				break;
			}
		  }
		return randomNum;
	}
	
	
	/**
	 * 生成指定范围内的随机数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumInRange(int min, int max){
		
		Random random = new Random();     
		return random.nextInt(max)%(max-min+1) + min;
	}
	
	
//	/**
//	 * @param args
//	 */
	public static void main(String[] args) {
//		System.out.println(getRandomPwd(6));
//		System.out.println(random(6));
//		System.out.println(getRandomNum(8));
//		System.out.println(Math.random());
        System.out.println(getRandomNumInRange(1,3));
		
	}

}
