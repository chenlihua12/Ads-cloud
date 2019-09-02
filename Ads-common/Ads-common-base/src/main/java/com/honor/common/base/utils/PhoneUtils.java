package com.honor.common.base.utils;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;
import com.honor.common.base.exception.PlugException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 手机号码处理工具
 */
@Slf4j
public class PhoneUtils {

    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    /**
     * 手机掩码
     * 11位的手机号为 15858313456 -> 158****3456
     * 10位的手机号为 1302345564 -> 130****564
     * 9位的手机号为 912345678 -> 91****678
     * 8位的手机号为 10098787 -> 10****87
     * 7位 1098787 -> 1****87
     *
     * @param phone
     * @return
     */
    public static String mask(String phone) {
        if (StringUtils.isEmpty(phone) || phone.length() < 7) {
            return phone;
        }
        int midIdx = phone.length() / 2;
        int leftOffset = midIdx - 2;
        int rightOffset = midIdx + 2;
        String left = phone.substring(0, leftOffset);
        String right = phone.substring(rightOffset, phone.length());
        return left + "****" + right;
    }

    /**
     * 根据国家代码和手机号  判断手机号是否有效
     *
     * @param phoneNumber
     * @param countryCode
     * @return
     */
    public static void checkPhoneNumber(String phoneNumber, String countryCode) {

        try {

            int areaCode = Integer.parseInt(countryCode);
            long phone = Long.parseLong(phoneNumber);
            PhoneNumber pn = new PhoneNumber();
            pn.setCountryCode(areaCode);
            pn.setNationalNumber(phone);

            if (!phoneNumberUtil.isValidNumber(pn)) {
                throw PlugException.INSTANCE(ErrorConstant.PARAMS_ERROR, I18nConstant.MOBILE_CHECK_NOT_PASS, "手机号校验不通过！");
            }
        } catch (Exception e) {
            log.error("PhoneUtils.checkPhoneNumber.phoneNumber={},countryCode={}", phoneNumber, countryCode, e);
            throw PlugException.INSTANCE(ErrorConstant.PARAMS_ERROR, I18nConstant.MOBILE_CHECK_NOT_PASS, "手机号校验不通过！");
        }
    }
}
