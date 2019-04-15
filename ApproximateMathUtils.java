package com.wodan.platform.foundation.util;

/**
 * 进行近似计算的工具类
 * 
 * @ClassName: ApproximateMathUtils
 * @author Administrator
 * @date 2015-8-24 上午10:31:25
 * @history
 * 
 */
public class ApproximateMathUtils {
	/**
	 * 计算近似值
	 * 
	 * @Description:
	 * @param num
	 *            待计算的值
	 * @param enlargementFactor
	 *            近似计算时扩大的倍数
	 * @param minAccuracy
	 *            近似计算要求的最低精度
	 * @param halfMinAccuracy
	 *            最低精度的一半
	 * @return
	 */
	public static double toApproximateValue(double num, long enlargementFactor, long minAccuracy, long halfMinAccuracy) {
		long langLatitude = double2Long(num, enlargementFactor);

		// 1). mod = X % step
		long mod = langLatitude % minAccuracy;
		if (mod == 0) {
			// 如果mod == 0，则x近似值就是x
			return num;
		} else if (mod <= halfMinAccuracy) {
			// 如果mod <= (step / 2)，则x近似值为 x - mod
			long result = langLatitude - mod;
			return long2Double(result, enlargementFactor);
		} else {
			// 如果mod >(step / 2)，则x近似值为x - mod + step
			long result = langLatitude - mod + minAccuracy;
			return long2Double(result, enlargementFactor);
		}
	}

	/**
	 * 近似计算中长整形转换为double型
	 * 
	 * @Description:
	 * @param result
	 * @param enlargementFactor
	 * @return
	 */
	private static double long2Double(long result, long enlargementFactor) {
		double doubleResult = result;
		return doubleResult / enlargementFactor;
	}

	/**
	 * 近似计算中double转换为长整形
	 * 
	 * @Description:
	 * @param min
	 * @param enlargementFactor
	 * @return
	 */
	private static long double2Long(double min, long enlargementFactor) {
		return (long) (min * enlargementFactor);
	}

	public static void main(String[] args) {
		double d = 39.90900;
		for (double i = 0.00001; i < 0.00101; i += 0.00001) {
			double approximateValue = toApproximateValue(d + i, 100000L, 25L, 25 / 2L);
			StringBuilder sb = new StringBuilder();
			sb.append(double2Long(d + i, 100000));
			sb.append(" = ");
			sb.append(double2Long(approximateValue, 100000));
			System.out.println(sb);
			
		}
	}
}
