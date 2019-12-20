package com.seckill.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 格式化工具类
 * @author mengxin
 * 2019-3-18
 *
 */
public class BigDecimalUtil {
	
	
	//保留两位小数点
	public static String format1(double value) {
		 
		 BigDecimal bd = new BigDecimal(value);
		 bd = bd.setScale(2, RoundingMode.HALF_UP);
		 return bd.toString();
	}
	//第二种方法,保留两位小数点
	public static String format2(double value) {
		String a=String.valueOf(value);
		DecimalFormat format=new DecimalFormat("0.00");
	
		 String b=format.format(new  BigDecimal(a));
		 return b;
	}
	
	
	
	public static void main(String[] args) {
			
		Double a=0.2000000003;
		Double b=Double.valueOf(format1(a));
		System.out.println(b);
		
	}
	

}
