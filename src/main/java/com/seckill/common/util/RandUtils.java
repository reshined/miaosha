package com.seckill.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class RandUtils {
	/**
	 * @param randName
	 * */
	public static BufferedImage getRandomImage(HttpServletRequest request,String randName) {
		int width = 125, height = 30;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		// 绘制背景
		g.setColor(getRandColor(200, 240));
		// 
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("宋体", Font.ITALIC + Font.BOLD, 30));


		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				g.setColor(getRandColor(0, 255));
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
		}

		int firstNum = random.nextInt(100);
		int secondNum = random.nextInt(100);
		int resultNum = firstNum + secondNum;
		String res = resultNum+"";
		String sRand = firstNum + "+"+secondNum+" =";
		
		for (int i = 0; i < sRand.length(); i++) {
			g.setColor(new Color(20 + random.nextInt(150), 20 + random
					.nextInt(150), 20 + random.nextInt(150)));
			g.drawString(sRand.substring(i,i+1), 15 * i + 5, 25);
		}
		request.getSession().setAttribute(randName, res);
		g.dispose();
		return image;
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
