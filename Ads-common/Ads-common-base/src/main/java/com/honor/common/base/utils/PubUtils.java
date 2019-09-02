package com.honor.common.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * The class Pub utils.
 *
 * @author ourblue
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PubUtils {
	/**
	 * The constant STRING_NULL.
	 */
	private final static String	STRING_NULL		= "-";
	/**
	 * 匹配手机号码, 支持+86和86开头
	 */
	private static final String	REGX_MOBILENUM	= "^((\\+86)|(86))?(13|15|17|18)\\d{9}$";

	/**
	 * 匹配邮箱帐号
	 */
	private static final String	REGX_EMAIL		= "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/**
	 * 匹配手机号码（先支持13, 15, 17, 18开头的手机号码）.
	 *
	 * @param inputStr
	 *            the input str
	 *
	 * @return the boolean
	 */
	public static Boolean isMobileNumber(String inputStr) {
		return !PubUtils.isNull( inputStr ) && inputStr.matches( REGX_MOBILENUM );
	}

	/**
	 * 判断对象是否Empty(null或元素为0) 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj
	 *            待检查对象
	 *
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null) {
			return true;
		}
		if (pObj == "") {
			return true;
		}
		if (pObj instanceof String) {
			return ((String) pObj).length() == 0;
		} else if (pObj instanceof Collection) {
			return ((Collection) pObj).isEmpty();
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() == 0;
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素大于0) 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj
	 *            待检查对象
	 *
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null) {
			return false;
		}
		if (pObj == "") {
			return false;
		}
		if (pObj instanceof String) {
			return ((String) pObj).length() != 0;
		} else if (pObj instanceof Collection) {
			return !((Collection) pObj).isEmpty();
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() != 0;
		}
		return true;
	}

	/**
	 * 判断一个或多个对象是否为空
	 *
	 * @param values
	 *            可变参数, 要判断的一个或多个对象
	 *
	 * @return 只有要判断的一个对象都为空则返回true, 否则返回false boolean
	 */
	public static boolean isNull(Object... values) {
		if (!PubUtils.isNotNullAndNotEmpty( values )) {
			return true;
		}
		for (Object value : values) {
			boolean flag;
			if (value instanceof Object[]) {
				flag = !isNotNullAndNotEmpty( (Object[]) value );
			} else if (value instanceof Collection<?>) {
				flag = !isNotNullAndNotEmpty( (Collection<?>) value );
			} else if (value instanceof String) {
				flag = isOEmptyOrNull( value );
			} else {
				flag = (null == value);
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Is o empty or null boolean.
	 *
	 * @param o
	 *            the o
	 *
	 * @return boolean boolean
	 */
	private static boolean isOEmptyOrNull(Object o) {
		return o == null || isSEmptyOrNull( o.toString() );
	}

	/**
	 * Is s empty or null boolean.
	 *
	 * @param s
	 *            the s
	 *
	 * @return boolean boolean
	 */
	private static boolean isSEmptyOrNull(String s) {
		return trimAndNullAsEmpty( s ).length() <= 0;
	}

	/**
	 * Trim and null as empty string.
	 *
	 * @param s
	 *            the s
	 *
	 * @return java.lang3.String string
	 */
	private static String trimAndNullAsEmpty(String s) {
		if (s != null && !s.trim().equals( STRING_NULL )) {
			return s.trim();
		} else {
			return "";
		}
		// return s == null ? "" : s.trim();
	}

	/**
	 * 判断对象数组是否为空并且数量大于0
	 *
	 * @param value
	 *            the value
	 *
	 * @return boolean
	 */
	private static Boolean isNotNullAndNotEmpty(Object[] value) {
		boolean bl = false;
		if (null != value && 0 < value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量大于0
	 *
	 * @param value
	 *            the value
	 *
	 * @return boolean
	 */
	private static Boolean isNotNullAndNotEmpty(Collection<?> value) {
		boolean bl = false;
		if (null != value && !value.isEmpty()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * Is email boolean.
	 *
	 * @param str
	 *            the str
	 *
	 * @return the boolean
	 */
	public static boolean isEmail(String str) {
		boolean bl = true;
		if (isSEmptyOrNull( str ) || !str.matches( REGX_EMAIL )) {
			bl = false;
		}
		return bl;
	}

	/**
	 * Uuid string.
	 *
	 * @return the string
	 */
	public synchronized static String uuid() {
		return UUID.randomUUID().toString().replace( "-", "" );
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase( trim( s ) )) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 身份证校验<br>
	 *
	 * @Author: yongheng
	 * @Date: 2018/10/22 20:35
	 */
	public static boolean isIDNumber(String IDNumber) {
		if (IDNumber == null || "".equals( IDNumber )) {
			return false;
		}
		// 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
				+ "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		// 假设18位身份证号码:41000119910101123X 410001 19910101 123X
		// ^开头
		// [1-9] 第一位1-9中的一个 4
		// \\d{5} 五位数字 10001（前六位省市县地区）
		// (18|19|20) 19（现阶段可能取值范围18xx-20xx年）
		// \\d{2} 91（年份）
		// ((0[1-9])|(10|11|12)) 01（月份）
		// (([0-2][1-9])|10|20|30|31)01（日期）
		// \\d{3} 三位数字 123（第十七位奇数代表男，偶数代表女）
		// [0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
		// $结尾

		// 假设15位身份证号码:410001910101123 410001 910101 123
		// ^开头
		// [1-9] 第一位1-9中的一个 4
		// \\d{5} 五位数字 10001（前六位省市县地区）
		// \\d{2} 91（年份）
		// ((0[1-9])|(10|11|12)) 01（月份）
		// (([0-2][1-9])|10|20|30|31)01（日期）
		// \\d{3} 三位数字 123（第十五位奇数代表男，偶数代表女），15位身份证不含X
		// $结尾

		boolean matches = IDNumber.matches( regularExpression );

		// 判断第18位校验值
		if (matches) {

			if (IDNumber.length() == 18) {
				try {
					char[] charArray = IDNumber.toCharArray();
					// 前十七位加权因子
					int[] idCardWi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
					// 这是除以11后，可能产生的11位余数对应的验证码
					String[] idCardY = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
					int sum = 0;
					for (int i = 0; i < idCardWi.length; i++) {
						int current = Integer.parseInt( String.valueOf( charArray[ i ] ) );
						int count = current * idCardWi[ i ];
						sum += count;
					}
					char idCardLast = charArray[ 17 ];
					int idCardMod = sum % 11;
					if (idCardY[ idCardMod ].toUpperCase().equals( String.valueOf( idCardLast ).toUpperCase() )) {
						return true;
					} else {
						System.out.println(
								"身份证最后一位:" + String.valueOf( idCardLast ).toUpperCase() + "错误,正确的应该是:" + idCardY[ idCardMod ].toUpperCase() );
						return false;
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println( "异常:" + IDNumber );
					return false;
				}
			}

		}
		return matches;
	}
}
