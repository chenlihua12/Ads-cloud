package com.honor.common.base.constant;

public class I18nConstant {

    public static final String SUCCESS = "sys.success";// 成功
    public static final String FAILURE = "sys.unknown_exception";//失败
    public static final String ENC_OR_DEC_EXCEPTION = "sys.encryption_or_decryption_exception";//加密或者解密异常
    public static final String PARAMS_ERROR = "sys.parameter_check_failed";//参数校验失败
    public static final String INVITE_CODE_ERROR = "sys.invite_code_error";//邀请码错误
    public static final String MOBILE_CHECK_NOT_PASS = "sys.mobile_check_not_pass";//手机号码校验不通过
    public static final String RANDOM_LENGTH_NEED_GT_0 = "sys.random_length_need_gt_0";//随机验证码长度必须大于零 ！
    public static final String HEADERS_ERROR = "sys.headers_error";//请求头异常 ！

    public static final String LOGIN_INFO_NOT_EXIST = "sys.login_info_not_exist";// 登录信息不存在！
    public static final String LOGIN_INFO_MODIFICATION = "sys.login_info_modification";// 登录信息被篡改！
    public static final String LOGIN_INFO_EXPIRED = "sys.login_info_expired";// 登录信息已过期 ！
    public static final String LOGIN_INFO_NOT_EXCEPTION ="sys.login_info_not_exception";// 登录信息解析异常！
    public static final String CREATE_TOKEN_EXCEPTION = "sys.create_token_exception";// 生成Token异常！
    public static final String PARSE_TOKEN_EXCEPTION = "sys.parse_token_exception";// 解析Token异常！
}
