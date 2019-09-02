/**
 * Project Name:fan-core-batch
 * File Name:RedisConstant.java
 * Package Name:com.fan.core.batch.common.constant
 * Date:2018年4月13日上午11:06:08
 * Copyright (c) 2018, 汇联基金 All Rights Reserved.
 */

package com.honor.common.base.constant;

/**
 * ClassName:RedisConstant <br/>
 * Description: TODO 添加描述. <br/>
 * Date: 2018年4月13日 上午11:06:08 <br/>
 *
 * @author ljx
 * @version
 * @since JDK 1.7
 * @see
 */
public class RedisConstant {
    // 项目前缀
    public static final String PRE = "_";

    // 会话TOKEN用户信息
    public static final String SESSION_TOKEN_LOGIN_USER_KEY = PRE + "session:token:login_user_key:";

    // 会话TOKEN
    public static final String SESSION_TOKEN_LOGIN_KEY = PRE + "session:token:login_key:";

    // 会话MOBILE
    public static final String SESSION_MOBILE_LOGIN_KEY = PRE + "session:mobile:login_key:";
}
