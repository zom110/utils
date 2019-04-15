package com.wodan.platform.foundation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateFormatUtils
 * 
 * @ClassName: DateFormatUtils
 * @author Administrator
 * @date 2015-1-12 下午7:52:37
 * @history
 * 
 */
public class DateFormatUtils extends org.apache.commons.lang.time.DateFormatUtils {
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将字符串转成日期
	 * 
	 * @Description:
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(date);
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 将字符串转成日期
	 * 
	 * @Description:
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseYYYYMMDD(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(date);
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 使用默认的pattern进行格式化
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, DEFAULT_PATTERN);
	}

	
	/**
	 * 进行格式化
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static String formatYYYYMMDD(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 进行格式化 10/1
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static String formatMMSlantDD(Date date) {
		return format(date, "MM/dd");
	}

	/**
	 * 进行格式 10月11日
	 * 
	 * @param date
	 * @return
	 */
	public static String formatChinaMMDD(Date date) {
		return format(date, "MM月dd日");
	}
	public static String formatChinaYYYYMMDD(Date date) {
		return format(date, "yyyy年MM月dd日");
	}

	/**
	 * 获取当天两个时间 相差的小时数，
	 * 
	 * @Description:
	 * @param time
	 * @return
	 */
	public static Double getMinusHours(Long beginTime, Long endTime) {

		int hour1 = Integer.parseInt(DateFormatUtils.format(new Date(beginTime), "HH"));
		int minute1 = Integer.parseInt(DateFormatUtils.format(new Date(beginTime), "mm"));

		int hour2 = Integer.parseInt(DateFormatUtils.format(new Date(endTime), "HH"));
		int minute2 = Integer.parseInt(DateFormatUtils.format(new Date(endTime), "mm"));

		int allMinutes = hour2 * 60 + minute2 - (hour1 * 60 + minute1);

		return allMinutes / 60.0;
	}

	public static int getHhMm2Int(Date time) {

		int hour1 = Integer.parseInt(DateFormatUtils.format(time, "HH"));
		int minute1 = Integer.parseInt(DateFormatUtils.format(time, "mm"));
		return (hour1 * 60 + minute1) * 60;
	}

	/**
	 * 时间戳转化为时间字符串
	 * 
	 * @Description:
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatLong2String(Long time, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		String formatStr = format.format(time);
		return formatStr;
	}

	// public static void main(String[] args) {
	//
	// System.out.println(format(new Date(), "MM/dd E"));
	//
	// }

}
