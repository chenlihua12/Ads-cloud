package com.honor.common.sso.util;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.honor.common.sso.dto.JwtConfigDto;
import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.dto.UserDto;
import com.honor.common.base.exception.CommonException;
import com.honor.common.base.utils.AESUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;

@Slf4j
public class JwtUtils {

    /**
     * 生成token
     *
     * @param userDto
     * @param jwtConfigDto
     * @return
     */
    public static String createToken(UserDto userDto, JwtConfigDto jwtConfigDto) {
        Calendar current = Calendar.getInstance();
        Date now = current.getTime();
        //失效时间设置
        current.add(Calendar.SECOND, jwtConfigDto.getExpired());
        Date exp = current.getTime();
        try {
            return create()
                    .withClaim("jti", UUID.randomUUID().toString())
                    .withClaim("sub", userDto.getLoginName())
                    .withClaim("areaCode", userDto.getAreaCode())
                    .withClaim("iat", now)
                    .withClaim("exp", exp)
                    .withClaim("uid", AESUtils.encrypt(userDto.getUserId().toString(), jwtConfigDto.getUidAesSecretKey()))
                    .sign(Algorithm.HMAC256(jwtConfigDto.getJwtSecretKey()));
        } catch (Exception e) {
            log.error("JwtUtils.createToken", e);
            throw CommonException.INSTANCE(ErrorConstant.CREATE_TOKEN_EXCEPTION, "生成Token异常！");
        }
    }


    /**
     * 解析TOKEN
     *
     * @param token
     * @param jwtSecretKey
     * @param uidAesSecretKey
     * @return
     */
    public static UserDto verifyToken(String token, String jwtSecretKey, String uidAesSecretKey) throws Exception {

        JWTVerifier verifier = require(Algorithm.HMAC256(jwtSecretKey)).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        return new UserDto(Long.valueOf(AESUtils.decrypt(((Claim) claims.get("uid")).asString(), uidAesSecretKey)), (claims.get("areaCode")).asString(), (claims.get("sub")).asString());
    }

    /**
     * 解析TOKEN
     *
     * @param token
     * @param jwtConfigDto
     * @return
     */
    public static UserDto verifyToken(String token, JwtConfigDto jwtConfigDto) {
        try {
            JWTVerifier verifier = require(Algorithm.HMAC256(jwtConfigDto.getJwtSecretKey())).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            return new UserDto(Long.valueOf(AESUtils.decrypt(((Claim) claims.get("uid")).asString(), jwtConfigDto.getUidAesSecretKey())), (claims.get("areaCode")).asString(), (claims.get("sub")).asString());
        } catch (Exception e) {
            log.error("JwtUtils.verifyToken.error", e);
            throw CommonException.INSTANCE(ErrorConstant.PARSE_TOKEN_EXCEPTION, "解析Token异常！");
        }
    }
}

