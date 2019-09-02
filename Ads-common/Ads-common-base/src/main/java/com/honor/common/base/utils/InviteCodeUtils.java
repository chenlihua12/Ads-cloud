package com.honor.common.base.utils;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;
import com.honor.common.base.exception.PlugException;

import java.util.regex.Pattern;

public class InviteCodeUtils {

    /**
     * 邀请码总共32个码[一般不允许变更，否则导致之前生成的邀请码无法正确反解析]
     */
    private static String CODE = "DZCA635VQEF9WPHBKRMGSXIU2JTNL7Y8";
    private static final String[] CODE_ARR = CODE.split("");

    /**
     * 初始化混淆数据[不能变]32*32*32*32*32-1 = 33554431L
     */
    private static final Long INIT_USER = 35755638L;

    /**
     * 最大用户ID
     */
    private static final Long MAX_USER_ID = 32 * 32 * 32 * 32 * 32 * 32 - INIT_USER;

    /**
     * 邀请码个数
     */
    private static final Integer INVITE_CODE_SIZE = 6;

    /**
     * 邀请码正则表达式
     */
    private static final String INVITE_CODE_REGEX = "^[ABCDEFGHIJKLMNPQRSTUVWXYZ2356789]{" + INVITE_CODE_SIZE + ",}$";

    /**
     * 用户ID生成邀请码
     *
     * @param userId
     * @return
     */
    public static String idToCode(Long userId) {

        if (userId == null || MAX_USER_ID < userId) {
            throw PlugException.INSTANCE(ErrorConstant.PARAMS_ERROR, I18nConstant.PARAMS_ERROR, "用户ID不正确 !");
        }

        //加上混淆ID的数据，用以生成邀请码
        int userInt = userId.intValue() + INIT_USER.intValue();

        int index;
        String inviteCode = "";
        for (int i = 0; i < INVITE_CODE_SIZE; i++) {
            index = userInt & 31;
            inviteCode = CODE_ARR[index] + inviteCode;
            userInt = userInt >> 5;
        }
        return inviteCode;
    }

    /**
     * 邀请码解析用户ID
     *
     * @param inviteCode
     * @return
     */
    public static Long codeToId(String inviteCode) {

        if (!validateInviteCode(inviteCode)) {
            throw PlugException.INSTANCE(ErrorConstant.PARAMS_ERROR, I18nConstant.INVITE_CODE_ERROR, "邀请码不正确 !");
        }

        String[] inviteCodeArr = inviteCode.split("");
        int userInt = 0;
        for (int i = 0; i < INVITE_CODE_SIZE; i++) {
            int index = CODE.indexOf(inviteCodeArr[i]);
            userInt = (userInt << 5) + index;
        }
        Long userId = userInt - INIT_USER;

        if (userId < 0) {
            throw PlugException.INSTANCE(ErrorConstant.PARAMS_ERROR, I18nConstant.INVITE_CODE_ERROR, "邀请码不正确 !");
        }
        return userId;
    }

    /**
     * 验证邀请码格式
     *
     * @param inviteCode 邀请码
     * @return 是否符合格式
     */
    public static boolean validateInviteCode(final String inviteCode) {
        return Pattern.matches(INVITE_CODE_REGEX, inviteCode);
    }
}
