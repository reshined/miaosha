package com.seckill.common.util;
/**
@author hby 2016年6月4日
 */
/**
 * 

* <p>Title: StatusCode</p>  

* <p>Description: 状态码</p>  

* @author Administrator  

* @date 2019年4月27日下午4:58:44
 */
public class StatusCode {
	//正常访问返回数据
	public static int OK = 100;
	//常规错误，错误信息可以在前端显示
	public static int COMMON_FAULT = 101;
	//用户token失效
	public static int USER_OUT = 102;
	//用户没有权限
	public static int NO_PRIVILEGE = 103;
	//服务器错误
	public static int SERVERS_FAULT = 104;
	//做过
	public static int HAS_DONE = 105;
	//提交参数不正确
	public static int PARAM_FAULT = 106;
	//提交订单为绑定手机号
	public static int NEED_PHONE = 107;
	//身份信息验证
	public static int ID_CHECK = 108;
	//请求非法
	public static int REQUEST_ILLEGAL = 109 ;
	//用户正在支付中
	public static int USERPAYING = 110;
	//新增错误码,订单不存在
	public static int ORDER_NULL=111;
	//当前油站订单无法进行开票
	public static int NO_INVOICE_PRIVALEGE=120;
}
