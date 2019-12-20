package com.seckill.common.util;

/**
 * 

* <p>Title: IdGenarator</p>  

* <p>Description: Id生成工具类</p>  

* @author Administrator  

* @date 2018年4月27日下午4:57:29
 */
public class IdGenarator {

	private IdGenarator() {

    }

    static final int DEFAULT_LENGTH = 10;
    static long seq = 0;

    /**
     * 得到10位的序列号,长度不足10位,前面补0
     * @return
     */
    public static String getSequence() {
        String str = String.valueOf(seq++);
        int len = str.length();
        //达到11位则重新开始
        if (len == 11) {
			seq = 0;
			len = 1;
			str = "0";
			seq++;
		}
        if (len >= DEFAULT_LENGTH) {// 取决于业务规模,应该不会到达10
            return str;
        }
        int rest = DEFAULT_LENGTH - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }
    
    /**
     * 得到length位的序列号,长度不足length位,前面补0
     * @return
     */
    public static String getSequence(Integer seq , Integer length) {
        String str = String.valueOf(seq++);
        int len = str.length();
        int rest = length - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }
}
