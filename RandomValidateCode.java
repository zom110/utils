package com.wodan.platform.foundation.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName:RandomValidateCode
 * @Description:生成验证码
 * @author zhangls
 * @date 2015-1-13 下午5:41:25
 * @history
 */
public class RandomValidateCode {

	public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";// 放到session中的key
	private static final String RAND_STRING_NUMBER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 随机产生的字符串
	private static final String RAND_NUMBER = "0123456789"; // 随机产生的字符串

	private static Random random = new Random();
	private static int width = 96;// 图片宽 原80
	private static int height = 35;// 图片高 原26
	private static int lineSize = 40;// 干扰线数量
	private static int stringNum = 4;// 随机产生字符数量

	private static Font font = new Font("Fixedsys", Font.CENTER_BASELINE, 22);
	private static Font fontBaseline = new Font("Times New Roman", Font.ROMAN_BASELINE, 22);

	/**
	 * 获得干扰颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 生成随机图片(数字和字符)
	 * 
	 * @return list[0] : 随机数字符串, list[1] : 随机数图片
	 */
	public static List<Object> getRandcode() {
		ArrayList<Object> arrayList = new ArrayList<>();
		String randomString = getRandomString(stringNum);
		BufferedImage image = drawImage(randomString);
		arrayList.add(randomString);
		arrayList.add(image);
		return arrayList;
	}

	/**
	 * 生成随机图片(只包含数字)
	 * 
	 * @return list[0] : 随机数字符串, list[1] : 随机数图片
	 */
	public static List<Object> getRandcodeByNumber() {
		ArrayList<Object> arrayList = new ArrayList<>();
		String randomString = getRandomNumber(stringNum);
		BufferedImage image = drawImage(randomString);
		arrayList.add(randomString);
		arrayList.add(image);
		return arrayList;
	}

	/**
	 * @Description: 绘画图片面板
	 * @param str
	 *            随机字符串
	 * @return
	 */
	private static BufferedImage drawImage(String str) {
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics(); // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);

		// 绘制干扰线
		drowLine(g);
		// 绘制随机字符
		drawString(g, str);

		g.dispose();
		return image;
	}

	/**
	 * 生成随机图片
	 */
	public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		List<Object> randcodeList = getRandcode();
		session.removeAttribute(RANDOMCODEKEY);
		session.setAttribute(RANDOMCODEKEY, randcodeList.get(0));
		session.setAttribute("randomString", randcodeList.get(0));
		try {
			ImageIO.write((BufferedImage) randcodeList.get(1), "JPEG", response.getOutputStream()); // 将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绘制字符串
	 */
	private static Graphics drawString(Graphics g, String str) {
		for (int i = 0; i < str.length(); i++) {
			g.translate(random.nextInt(3), random.nextInt(3));
			g.setFont(font);
			g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
			g.drawString(String.valueOf(str.charAt(i)), 16 * (i + 1), 23);
		}
		return g;
	}

	/**
	 * 绘制干扰线
	 */
	private static Graphics drowLine(Graphics g) {
		g.setColor(getRandColor(110, 133));
		g.setFont(fontBaseline);
		for (int i = 0; i <= lineSize; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(13);
			int yl = random.nextInt(15);
			g.drawLine(x, y, x + xl, y + yl);
		}
		return g;
	}

	/**
	 * @Description: 获取随机的字符
	 * @param length
	 *            随机字符串长度
	 * @return
	 */
	private static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(RAND_STRING_NUMBER.charAt(random.nextInt(RAND_STRING_NUMBER.length())));
		}
		return sb.toString();
	}

	/**
	 * @Description: 获取随机的字符
	 * @param length
	 *            随机字符串长度
	 * @return
	 */
	private static String getRandomNumber(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(RAND_NUMBER.charAt(random.nextInt(RAND_NUMBER.length())));
		}
		return sb.toString();
	}

}
