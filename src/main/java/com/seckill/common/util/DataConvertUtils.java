package com.seckill.common.util;

/**
 * 数据转换工具类
 * @author Administrator
 * @date 2018-04-25 14:45:02
 */
public class DataConvertUtils {

	/**
	 * double 转换成Integer ，并扩大100倍
	 * @param d
	 * @return
	 */
	public static Integer double2IntegerEnlarge100(Double d){
		String s = String.valueOf(d * 10 * 10);
		Integer i = Integer.valueOf(s.substring(0, s.indexOf(".")));
		return i;
	}
}
