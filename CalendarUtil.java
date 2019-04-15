package com.wodan.platform.foundation.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Assert;

/**
 * @ClassName:MainDateUtil
 * @Description:
 * @author zhangls
 * @date 2015-2-3 上午11:12:28
 * @history
 */
public class CalendarUtil {

	public final static long TIME_MILLIS_HOUR_72 = 72 * 60 * 60 * 1000L; // 72小时
	public final static long TIME_MILLIS_HOUR_48 = 48 * 60 * 60 * 1000L; // 48小时
	public final static long TIME_MILLIS_HOUR_24 = 24 * 60 * 60 * 1000L; // 24小时
	public final static long TIME_MILLIS_HOUR_1 = 1 * 60 * 60 * 1000L; // 1小时
	public final static long TIME_MILLIS_MINUTE_5 = 5 * 60 * 1000L; // 5分钟

	/**
	 * 获取日期中的年份
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static int getYearFromDate(Date date) {

		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		return calen.get(Calendar.YEAR);
	}

	/**
	 * 获取日期中的月份
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static int getMonthFromDate(Date date) {

		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		return calen.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当天开始时间
	 * 
	 * @Description:
	 * @return
	 */
	public static Date getTodayBeginTime() {
		Calendar calen = Calendar.getInstance();
		calen.set(Calendar.HOUR_OF_DAY, 0);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获取当天的结束时间
	 * 
	 * @Description:
	 * @return
	 */
	public static Date getTodayEndTime() {
		Calendar calen = Calendar.getInstance();
		calen.set(Calendar.HOUR_OF_DAY, 23);
		calen.set(Calendar.MINUTE, 59);
		calen.set(Calendar.SECOND, 59);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获取当日期 前后日期的开始时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表明天 -1代表昨天 ...
	 * @return
	 */
	public static Date getBeginTimeOffsetDay(int offset) {
		Calendar calen = Calendar.getInstance();
		calen.add(Calendar.DATE, offset);
		calen.set(Calendar.HOUR_OF_DAY, 0);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获取某个时间 前后偏移N个日期的时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表明天 -1代表昨天 ...
	 * @return
	 */
	public static Date getDateTimeOffsetDay(Date date, int offset) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		if (offset != 0) {
			calen.add(Calendar.DATE, offset);
		}
		return calen.getTime();
	}

	/**
	 * 获当前日期时间 前/后天数的结束时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表明天 -1代表昨天 ...
	 * @return
	 */
	public static Date getEndTimeOffsetDay(Date date, int offset) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		if (offset != 0) {
			calen.add(Calendar.DATE, offset);
		}
		calen.set(Calendar.HOUR_OF_DAY, 23);
		calen.set(Calendar.MINUTE, 59);
		calen.set(Calendar.SECOND, 59);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获某个日期所在月 /前后月份的开始时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表下个月 0代表本月 -1代表上个月 ...
	 * @return
	 */
	public static Date getFirstDayOffsetMonth(Date date, int offset) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (offset != 0) {
			cal.add(Calendar.MONTH, offset);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取某个日期所在月的 最后一天
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {

		Calendar cal = Calendar.getInstance();
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, daysInMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获当前日期时间 前/后月份的时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表下个月 0代表本月 -1代表上个月 ...
	 * @return
	 */
	public static Date getDateTimeOffsetMonth(Date date, int offset) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (offset != 0) {
			cal.add(Calendar.MONTH, offset);
		}
		return cal.getTime();
	}

	/**
	 * 获当前日期时间 前/后月份的结束时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表下个月 0代表本月 -1代表上个月 ...
	 * @return
	 */
	public static Date getEndTimeOffsetMonth(Date date, int offset) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		if (offset != 0) {
			calen.add(Calendar.MONTH, offset);
		}
		calen.set(Calendar.HOUR_OF_DAY, 23);
		calen.set(Calendar.MINUTE, 59);
		calen.set(Calendar.SECOND, 59);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获当前日期时间 前/后N小时的时间
	 * 
	 * @Description:
	 * @param offset
	 *            1 代表下个月 0代表本月 -1代表上个月 ...
	 * @return
	 */
	public static Date getDateTimeOffsetHour(Date date, int offset) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (offset != 0) {
			cal.add(Calendar.HOUR_OF_DAY, offset);
		}
		return cal.getTime();
	}

	/**
	 * 获取一个月后的时间
	 * 
	 * @Description:
	 * @return
	 */
	public static Date nextMonth() {

		Calendar calen = Calendar.getInstance();
		// 下面的就是把当前日期加一个月
		calen.add(Calendar.MONTH, 1);
		calen.set(Calendar.HOUR_OF_DAY, 0);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();

	}

	/**
	 * 获取一个月后的时间
	 * 
	 * @Description:
	 * @return
	 */
	public static Date lastMonth() {

		Calendar calen = Calendar.getInstance();
		// 下面的就是把当前日期加一个月
		calen.add(Calendar.MONTH, -1);
		calen.set(Calendar.HOUR_OF_DAY, 0);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();

	}

	/**
	 * 获取某个日期所在周的 第几天的时间
	 * 
	 * @Description:
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getDayInWeek(Date date, int num) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, num);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前日期是月份中第几天
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static Integer getDayNumOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取某一天的某个时间
	 * 
	 * @Description:
	 * @return
	 */
	public static Date getDateTimeInOneDay(Date date, int hour) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.set(Calendar.HOUR_OF_DAY, hour);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获取某一时间所在日期的结束时间
	 * 
	 * @Description:
	 * @return
	 */
	public static Date getEndTimeOfDay(Date date) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.set(Calendar.HOUR_OF_DAY, 23);
		calen.set(Calendar.MINUTE, 59);
		calen.set(Calendar.SECOND, 59);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获取d天后的某个时间点的时间
	 * 
	 * @Description:
	 * @param
	 */
	public static Date getDayHourTime(int d, int n) {

		Calendar calen = Calendar.getInstance();
		calen.add(Calendar.DATE, d);
		calen.set(Calendar.HOUR_OF_DAY, n);
		calen.set(Calendar.MINUTE, 0);
		calen.set(Calendar.SECOND, 0);
		calen.set(Calendar.MILLISECOND, 0);
		return calen.getTime();
	}

	/**
	 * 获取两个日期内相差多少天
	 * 
	 * @Description:
	 * @param date
	 * @return
	 */
	public static int getBetweenDay(Date date1, Date date2) {
		final long time1 = date1.getTime();
		final long time2 = date2.getTime();

		final long interval = Math.abs(time1 - time2);

		long result = interval / TimeUnit.DAYS.toMillis(1);

		return (int) result;
	}

	/**
	 * @Description 该方法用于 处理岗位刷新时间的显示格式
	 * @param timeMillis
	 * @return
	 */
	public static String calcRefreshTimeInfo(Long timeMillis) {
		if (timeMillis == null) {
			return null;
		}
		long currentTimeMillis = System.currentTimeMillis();
		long interval = currentTimeMillis - timeMillis; // 间隔时间

		DateFormat format = new SimpleDateFormat("MM/dd");
		String refreshStr = format.format(timeMillis); // 刷新时间的日期
		String todayStr = format.format(currentTimeMillis); // 今天的日期
		String yesStr = format.format(currentTimeMillis - TIME_MILLIS_HOUR_24); // 昨天的日期
		String beforeYesStr = format.format(currentTimeMillis - TIME_MILLIS_HOUR_48); // 前天的日期

		if (interval <= TIME_MILLIS_MINUTE_5) { // 小于5分钟
			return "刚刚";
		}

		if (interval <= TIME_MILLIS_HOUR_1) { // 5分钟~1小时
			return interval / 60000 + "分钟前";
		}

		if (refreshStr.equals(todayStr)) {
			// return "今天";
			return interval / (60 * 60 * 1000) + "小时前";
		}

		// DateFormat timeFormat = new SimpleDateFormat("HH:mm");

		if (refreshStr.equals(yesStr)) {
			return "昨天 ";// + timeFormat.format(timeMillis)
		}

		if (refreshStr.equals(beforeYesStr)) {
			return "前天";// + timeFormat.format(timeMillis)
		}
		return refreshStr;
	}

	// 删除时间中的时分秒，结果以日期的毫秒数传回，参数为毫秒数
	public static long deleteTimeHMS(Long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime().getTime();
	}

	// 删除时间中的时分秒，结果以日期的毫秒数传回，参数为毫秒数
	public static Date deleteTimeHMS(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	// 返回日期时分秒的毫秒数，参数为毫秒数
	public static Integer getTimeHMS(Long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));

		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute = calendar.get(Calendar.MINUTE);
		Integer second = calendar.get(Calendar.SECOND);

		return 1000 * ((hour * 60 + minute) * 60 + second);
	}

	public static Date deleteTimeYMD(Date start) {
		Assert.notNull(start);

		Calendar today = Calendar.getInstance();
		today.setTime(start);
		today.set(Calendar.YEAR, 0);
		today.set(Calendar.MONTH, 0);
		today.set(Calendar.DAY_OF_YEAR, 0);

		return today.getTime();
	}

	public static Date getFirstDayOffsetYear(Date date, int offset) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (offset != 0) {
			cal.add(Calendar.YEAR, offset);
		}
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 增加天数
	public static Date addDay(Date date, int days) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);

		return cal.getTime();
	}

	public static void main(String[] args) {
		// Date today = new Date();
		//
		// Calendar c = Calendar.getInstance();
		// c.add(Calendar.DAY_OF_MONTH, -555);
		//
		// System.out.println(getDateTimeOffsetMonth(12));
		// System.out.println(getFirstDayOffsetYear(new Date(),-1));

		// Date currTime = new Date();
		// System.out.println(getDateTimeOffsetDay(currTime, -1));

		// List<Date> shouldRefreshTime = new LinkedList<>();
		// int jobSpreadNum = 6;
		// Date firstDateTime = currTime; // 第一个时间点
		// Date secondDateTime = new Date(currTime.getTime()+6*60*60*1000); //
		// 第二个时间点，6小时之后的时间
		// shouldRefreshTime.add(secondDateTime);
		//
		// for (int i = 1; i < jobSpreadNum-1; i++) {
		// if(i%2 != 0){
		// // 第一个时间点偏移
		// shouldRefreshTime.add(CalendarUtil.getDateTimeOffsetDay(firstDateTime, 1));
		// firstDateTime = CalendarUtil.getDateTimeOffsetDay(firstDateTime, 1);
		// }else{
		// // 第二个时间点偏移
		// shouldRefreshTime.add(CalendarUtil.getDateTimeOffsetDay(secondDateTime, 1));
		// secondDateTime = CalendarUtil.getDateTimeOffsetDay(secondDateTime, 1);
		// }
		// }
		//
		// System.out.println(shouldRefreshTime);

		// System.out.println(getFirstDayOffsetMonth(new Date(), 1));
		// System.out.println(getYearFromDate(getFirstDayOffsetMonth(new Date(), -2)));

		// System.out.println(getDateTimeOffsetHour(new Date(), -1));
	}

}
