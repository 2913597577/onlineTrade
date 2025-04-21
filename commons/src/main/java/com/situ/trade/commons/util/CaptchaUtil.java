package com.situ.trade.commons.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Verification Code, 支持Base64
 * @author bobzyh
 *
 */
public class CaptchaUtil {
	/*
	 * 宽度
	 * 高度
	 * 长度
	 * 字符集
	 */
	private static final int WIDTH = 100;
	private static final int HEIGHT = 38;
	private static final int CODE_LENGTH = 4;
	private static final String CODES = "1234567890ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static final int LINE_COUNT = 15;
	
	
	/**
	 * 
	 * @param response 向客户端返回图片
	 * @return 返回验证码的字符串
	 */
	public static String createCaptcha(HttpServletResponse response) {
		return createCaptcha(WIDTH, HEIGHT, CODE_LENGTH, response);
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @param codeLength
	 * @param response
	 * @return
	 */

	public static String createCaptcha(int width, int height, int codeLength, HttpServletResponse response) {
		// 生成字符串
		String captcha = randomCode(codeLength);
		
		// 生成图片  1+1=？
		BufferedImage image = createImage(width, height, captcha);
		
		// 写入Response
		try {
			ImageIO.write(image, "jpeg", response.getOutputStream());
			
			return captcha;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 只创建图片
	 * @param captcha
	 * @return
	 */
	public static BufferedImage createImage(String captcha){
		return createImage(WIDTH, HEIGHT, captcha);
	}

	public static BufferedImage createImage(int width, int height, String captcha) {
		int codeLength = captcha.length();

		// 生成图片  1+1=？
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取画板
		Graphics graphics = image.getGraphics();

		// 绘制背景颜色
		graphics.setColor(randomBgColor());
		graphics.fillRect(0, 0, width, height);

		// 在 "画板"上生成干扰线条
		drawLine(width, height, graphics, LINE_COUNT);


		// 绘制字符串
		graphics.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		for (int i = 0; i < captcha.length(); i++) {
			graphics.setColor(randomFontColor());
			graphics.drawString(captcha.substring(i, i+1), i*(width/codeLength), (height)/2+30/6*2);
		}

		// 释放资源
		graphics.dispose();

		return image;
	}

	public static String createBase64(String captcha){
		BufferedImage imgae = createImage(captcha);
		return getBase64(imgae);
	}

	public static String getBase64(BufferedImage image){
		String base64 = null;

		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", stream);
			base64 = "data:image/jpeg;base64," + Base64.encodeBase64String(stream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64;
	}


	/**
	 * 绘制干扰线
	 * @param width
	 * @param height
	 * @param graphics
	 * @param count
	 */
	private static void drawLine(int width, int height, Graphics graphics, int count) {
		Random random = new Random();
		
		for (int i = 0; i < count; i++) {
		    graphics.setColor(randomFontColor());
		    final int x = random.nextInt(width);
		    final int y = random.nextInt(height);
		    final int w = random.nextInt(width);
		    final int h = random.nextInt(height);
		    graphics.drawLine(x, y, w, h);
		}
		
	}

	private static Color randomFontColor() {
		return randomColor(150, 0);
	}

	/**
	 * 随机生成背景
	 * @return
	 */
	private static Color randomBgColor() {
		return randomColor(256, 200);
	}
	
	
	/**
	 * 随机生成颜色
	 * @param maxValue
	 * @param minValue
	 * @return
	 */
	private static Color randomColor(int maxValue, int minValue) {
		Random random = new Random();
		
		int r = minValue+random.nextInt(maxValue-minValue);
		int g = minValue+random.nextInt(maxValue-minValue);
		int b = minValue+random.nextInt(maxValue-minValue);
		
		Color color = new Color(r, g, b);
		
		return color;
	}

	/**
	 * 生成验证码，默认长度4
	 * @return
	 */
	public static String randomCode(){
		return randomCode(CODE_LENGTH);
	}
	/**
	 * 生成字符串
	 * @param codeLength
	 * @return
	 */
	public static String randomCode(int codeLength) {
		
		StringBuilder captcha = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i < codeLength; i++) {
			int index = random.nextInt(CODES.length());
			captcha.append(CODES.charAt(index));
		}
		
		return captcha.toString();
	}
		
}
