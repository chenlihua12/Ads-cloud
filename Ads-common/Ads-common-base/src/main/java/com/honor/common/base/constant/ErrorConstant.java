package com.honor.common.base.constant;

public class ErrorConstant {

	public static final Integer	SUCCESS						= 0;	// 成功
	public static final Integer	FAILURE						= -1;	// 失败,可提示
	public static final Integer	EXCEPTION_FAILURE			= 500;	// 程序错误
	public static final Integer	PARAMS_ERROR				= 1;	// 参数校验失败
	public static final Integer	PARSE_ERROR					= 2;	// 解析异常

	public static final Integer	HEADERS_INFO_ERROR			= 10;	// headers不正确

	public static final Integer	LOGIN_INFO_NOT_EXIST		= 20;	// 登录信息不存在！
	public static final Integer	LOGIN_INFO_MODIFICATION		= 21;	// 登录信息被篡改！
	public static final Integer	LOGIN_INFO_EXPIRED			= 22;	// 登录信息已过期 ！
	public static final Integer	LOGIN_INFO_NOT_EXCEPTION	= 23;	// 登录信息解析异常！
	public static final Integer	CREATE_TOKEN_EXCEPTION		= 24;	// 生成Token异常！
	public static final Integer	PARSE_TOKEN_EXCEPTION		= 25;	// 解析Token异常！
	public static final Integer	MULTIPLE_LOGIN_FAIL			= 26;	// 您已在其它设备登录,请重新登录
	public static final Integer	LOGIN_IP_DIFF				= 27;	// 不同ip登录，需要发送短信

	public static final Integer NOT_TRADE_PASSWORD			= 28;	// 没有设置交易密码
	public static final Integer NOT_MERCHANT				= 100;	// 没有承兑商
	public static final Integer STOP_ALLOT					= 101;	// 暂停分单
	public static final Integer ORDER_STATUS_MODIFICATION	= 102;	// 订单状态已改变

	public static final Integer APP_AUTO_CONFIRM            = 103;  // App不显示弹窗

	public static final Integer ORDER_HAS_EXPIRED           = 104;  //订单已过期

}
