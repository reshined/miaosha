package com.seckill.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
	public static long timeToStemp(String time) throws ParseException{//日期转为时间戳
	     SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date=simpleDateFormat .parse(time);
	     long timeStemp = (long) date.getTime();
	     return timeStemp;
	}
	
	public static String stampToDate(String s){//时间戳转为日期
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long lt = new Long(s);
	        Date date = new Date(lt);
	        res = simpleDateFormat.format(date);
	        return res;
	 }
	
	public static String getTime(long limitedTime) {
		  long gapSecondTime = limitedTime/1000;  
          int d = (int) gapSecondTime/(24*60*60); // 换算成小时数  
          int h = (int)(gapSecondTime/(60*60)%24); //换算成分钟数，以24小时切分，多出的部分就是要显示的分钟数  
          int m = (int)(gapSecondTime/(60)%60);//换算成分钟  
          int s = (int)(gapSecondTime%60);//本身gapSecondTime就是秒数，mod 60 获得的就是剩余秒数
          String time = "剩余时间："+d+"天"+h+"小时"+m+"分钟"+s+"秒";
          return time;
	 }

	 //时间转字符串
	public static String timeToStr(Date date) throws ParseException{//日期转为时间戳
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr=simpleDateFormat.format(date);
		return datestr;
	}

	//时间转字符串
	public static String timeToStr2(Date date) throws ParseException{//日期转为时间戳
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
		String datestr=simpleDateFormat.format(date);
		return datestr;
	}

	public static String timeToStr3(Date date) throws ParseException{//日期转为时间戳
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd");
		String datestr=simpleDateFormat.format(date);
		return datestr;
	}

	public static String timeToStr4(Date date) throws ParseException{//日期转为时间戳
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM");
		String datestr=simpleDateFormat.format(date);
		return datestr;
	}


	/**
	 * 字符串转换为时间
	 * @param s
	 * @return
	 */
	public static Date strToDate(String s){
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//必须捕获异常
		Date date=null;
		try {
			date=sDateFormat.parse(s);
			System.out.println(date);
		} catch(ParseException px) {
			px.printStackTrace();
		}
		return date;
	}


	public static Date strToDate2(String s){
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		//必须捕获异常
		Date date=null;
		try {
			date=sDateFormat.parse(s);
			System.out.println(date);
		} catch(ParseException px) {
			px.printStackTrace();
		}
		return date;
	}


	public static Date strToDate3(String s){
		SimpleDateFormat sDateFormat=new SimpleDateFormat("dd");
		//必须捕获异常
		Date date=null;
		try {
			date=sDateFormat.parse(s);
			System.out.println(date);
		} catch(ParseException px) {
			px.printStackTrace();
		}
		return date;
	}

	/**
	 * 将时间+day天后的时间
	 * @param date 转换的时间
	 * @param day 天数
	 * @return
	 */
	public static Date timeAddDay(Date date,int day){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,day);
		return calendar.getTime();
	}

	/**
	 * 将时间+month个月
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date timeAddMonth(Date date,int month){
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 将时间+year个年
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date timeAddYear(Date date,int year){
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR,year);
		return calendar.getTime();
	}


	/**
	 *
	 * @param nowTime   当前时间
	 * @param startTime	开始时间
	 * @param endTime   结束时间
	 * @return
	 * @author sunran   判断当前时间在时间区间内
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}


	public static void main(String[] args) throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		Date startTime = ft.parse("2019-06-05 03:26:54");
		Date endTime = ft.parse("2019-06-09 03:26:54");
		Date nowTime = new Date();
		boolean effectiveDate = isEffectiveDate(nowTime, startTime, endTime);
		if (effectiveDate) {
			System.out.println("当前时间在范围内");
		}else {
			System.out.println("当前时间在不在范围内");
		}
	}

}
