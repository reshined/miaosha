package com.seckill.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.misc.BASE64Encoder;

/**
 * 

* <p>Title: Utils</p>  

* <p>Description: 增加相关工具类</p>  

* @author Administrator  

* @date 2018年4月27日下午4:59:03
 */
public class Utils {
	/**
	 * md5编码,采用Base64编码
	 * */
	public static String md5Econde(String str){
		if(str == null){
			return null;
		}
		byte[] b;
		try {
			b = DigestUtils.md5Digest(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(b);
	}
	
	/**
	 * 生成验证码
	 * */
	public static String getSCode(){
		Integer i = (int)(((Math.random()*9)+1)*100000);
		return i.toString();
	}
	/**
	 * 生成指定位数的验证码
	 * */
	public static String getRandomSCode(int j){
		StringBuffer buffer = new StringBuffer();
		int head=(int)(Math.random()*10);
		if(head==0){
			head++;
		}
		buffer.append(head);
		for (int i = 0; i < j; i++) {
			int a=(int)(Math.random()*10);
			buffer.append(a);
		}
		return buffer.toString();
	}
	/**
	 * 生成指定1到指定数字范围内的随机数
	 * @param i
	 * @return
	 */
	public static int getRandomNum(int i){
		Random r = new Random();
		return r.nextInt(i);
	}
	/**
	 * 以当天凌晨为时间点增加天数获得日期
	 * */
	public static Date addDayAtZero(int i){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.add(Calendar.DAY_OF_MONTH, i);
		return calendar.getTime();
	}
	/**
	 * 以当周凌晨为时间点增加周数获得日期
	 * */
	public static Date addWeekAtZero(int i){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_WEEK, 0);
		
		calendar.add(Calendar.WEEK_OF_MONTH, i);
		return calendar.getTime();
	}
	/**
	 * 以当月凌晨为时间点增加月数获得日期
	 * */
	public static Date addMoonAtZero(int i){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();
	}
	/**
	 * 以当年凌晨为时间点增加年数获得日期
	 * */
	public static Date addYearAtZero(int i){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 1);
		
		calendar.add(Calendar.YEAR, i);
		return calendar.getTime();
	}
	/**
	 * 计算i天的毫秒值
	 * */
	public static Long shortOfDay(int i){
		return 86400000L*i;
	}
	/**
	 * 更换编码
	 * */
	public static String changeIOS88591ToUTF8(String keyWord){
		if(keyWord==null){
			return null;
		}
		byte[] bytes;
		try {
			bytes = keyWord.getBytes("ISO-8859-1");
			String change = new String(bytes,"UTF-8");
			return change;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/***
	 * 根据当前时间差获得中文时间日期
	 * */
	public static String timeForChinese(Date date){
		long shortTime = new Date().getTime() - date.getTime();
		//十分钟以内,为刚刚
		if(shortTime<600000){
			return "刚刚";
		} else if(shortTime >= 600000 && shortTime < 3600000){
			//分钟前
			int miunte = (int) (shortTime/(60000));
			return miunte + "分钟前";
		} else if(shortTime >= 3600000&& shortTime < 86400000){
			//小时前
			int hour = (int) (shortTime/(3600000));
			return hour+"小时前";
		} else if(shortTime >= 86400000 && shortTime < 2592000000L){
			//天
			int day = (int) (shortTime/86400000);
			return day+"天前";
		}else if(shortTime >= 2592000000L && shortTime < 31104000000L){
			int month = (int) (shortTime/(2592000000L));
			return month+"月前";
		}else {
			int year = (int) (shortTime/(31104000000L));
			return year+"年前";
		}
	}
	/**
	 * 根据当前时间差获得一定格式的时间日期
	 * */
	public static String timeForHH(Date date){
		if(date.after(addDayAtZero(0))){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			return "今天 "+format.format(date);
		}else if(date.before(addDayAtZero(0))&&date.after(addDayAtZero(-1))){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			return "昨天 "+format.format(date);
		}else{
			SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
			return format.format(date);
		}
	}
	/**
	 * 计算num1与num2的百分比
	 * */
	public static String precent(int num1,int num2){
		if(num2==0){
			return "-%";
		}
		Double p = (num1*1.0)/num2;
		int p2 = (int) (p*10000);
		Double p3 = p2/100.0;
		return p3+"%";
	}
	/**
	 * 替换字符
	 * */
	public static String replaceString(String res,String oldPart,String newPart){
		if(res==null){
			return null;
		}
		return res.replace(oldPart, newPart);
	}
	/**
	 * 四舍五入两位小数
	 * */
	public static Double makeDoubleNum(Double val){
		//方案二:  
		DecimalFormat df = new DecimalFormat("#.##");    
		Double get_double = Double.parseDouble(df.format(val));  
		return get_double;
	}
	public static void writeJson(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		String s;
		try {
			s = objectMapper.writeValueAsString(obj);
			System.out.println(s);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	public static String formatyyyyMMddHHmmss(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	/**
	 * 按照指定格式格式化日期
	 * @param date 待格式化的日期
	 * @param pattern 格式化规则
	 * @return 格式化后的日期
	 */
	public static String format(Date date , String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
	/**
	 * 按照指定格式格式化日期
	 * @param date 待格式化的日期
	 * @param pattern 格式化规则
	 * @return 格式化后的日期
	 */
	public static Date parse(String date , String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** 
     * 18位身份证校验,比较严格校验 
     * @author lyl 
     * @param idCard 
     * @return 
     */  
    public static boolean is18ByteIdCardComplex(String idCard){  
//        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");   
        Pattern pattern1 = Pattern.compile("^[1-9][0-7]\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$");   
        Matcher matcher = pattern1.matcher(idCard);  
        int[] prefix = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};  
        int[] suffix = new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };  
        if(matcher.matches()){  
            Map<String, String> cityMap = initCityMap();  
            if(cityMap.get(idCard.substring(0,2)) == null ){  
                return false;  
            }  
            int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和  
            for(int i=0;i<17;i++){  
                idCardWiSum+=Integer.valueOf(idCard.substring(i,i+1))*prefix[i];  
            }  
              
            int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置  
            String idCardLast=idCard.substring(17);//得到最后一位身份证号码  
              
            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X  
            if(idCardMod==2){  
                if(idCardLast.equalsIgnoreCase("x")){  
                    return true;  
                }else{  
                    return false;  
                }  
            }else{  
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码  
                if(idCardLast.equals(suffix[idCardMod]+"")){  
                    return true;  
                }else{  
                    return false;  
                }  
           }  
        }  
        return false;  
    }
    
    private static Map<String, String> initCityMap(){  
        Map<String, String> cityMap = new HashMap<String, String>();  
            cityMap.put("11", "北京");  
            cityMap.put("12", "天津");  
            cityMap.put("13", "河北");  
            cityMap.put("14", "山西");  
            cityMap.put("15", "内蒙古");  
              
            cityMap.put("21", "辽宁");  
            cityMap.put("22", "吉林");  
            cityMap.put("23", "黑龙江");  
              
            cityMap.put("31", "上海");  
            cityMap.put("32", "江苏");  
            cityMap.put("33", "浙江");  
            cityMap.put("34", "安徽");  
            cityMap.put("35", "福建");  
            cityMap.put("36", "江西");  
            cityMap.put("37", "山东");  
              
            cityMap.put("41", "河南");  
            cityMap.put("42", "湖北");  
            cityMap.put("43", "湖南");  
            cityMap.put("44", "广东");  
            cityMap.put("45", "广西");  
            cityMap.put("46", "海南");  
              
            cityMap.put("50", "重庆");  
            cityMap.put("51", "四川");  
            cityMap.put("52", "贵州");  
            cityMap.put("53", "云南");  
            cityMap.put("54", "西藏");  
              
            cityMap.put("61", "陕西");  
            cityMap.put("62", "甘肃");  
            cityMap.put("63", "青海");  
            cityMap.put("64", "宁夏");  
            cityMap.put("65", "新疆");  
              
//          cityMap.put("71", "台湾");  
//          cityMap.put("81", "香港");  
//          cityMap.put("82", "澳门");  
//          cityMap.put("91", "国外");  
//          System.out.println(cityMap.keySet().size());  
            return cityMap;  
        }
    public static boolean checkIDCardName(String name){
    Pattern pattern1 = Pattern.compile("^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$");
    Matcher matcher = pattern1.matcher(name);
    if(matcher.matches()){
    	return true;
    }
    return false;
    }
    
    //admin:[1]-manage:[]-store:[]
    @SuppressWarnings("unchecked")
	public static Map<String, List<String>> getPrivilegesByName(String string,String permission) {
    	if(StringUtils.isBlank(string)){
    		return null;
    	}
    	if(StringUtils.isBlank(permission)){
    		return null;
    	}
    	String[] permissions =permission.split("-");
    	Map<String,List<String>> map  = new HashMap<String,List<String>>();
    	for (String p : permissions) {
			if(p.indexOf(string)!=-1){
				//admin:[1,2,3]
				String values =  p.substring(p.indexOf("[")+1,p.length()-1);
				if(StringUtils.isBlank(values)){
					return  (Map<String, List<String>>) map.put(string,new ArrayList<String>());
				}
				List<String> list = new ArrayList<>();
				for (String s : values.split(",")) {
					list.add(s);
				}
				map.put(string, list);
				break;
			}
		}
		return map;
	}
    
    public static void main(String[] args) {
//		System.out.println(checkIDCardName("张三地就问问第三"));
		System.out.println(is18ByteIdCardComplex("511126200002026244"));
//    	String s = "admin:[1]-manager:[]-store:[1,2,3,4]";
//    	Map<String, List<String>> privilegesByName = getPrivilegesByName("manager",s);
//    	System.out.println(privilegesByName);
//    	for(int i=0;i<10;i++){
//    		
//    		System.out.println(getRandomNum(1));
//    	}
//    	System.out.println(Utils.addDayAtZero(0));
//    	System.out.println(Utils.md5Econde("123456"));
	}
    
}
