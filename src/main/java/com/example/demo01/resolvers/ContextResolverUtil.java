package com.example.demo01.resolvers;

import com.example.demo01.jwt.provider.JwtTokenProvider;
import com.example.demo01.jwt.provider.Payload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.util.StringUtils;

/**
 * Created by konghang on 2017/8/31.
 */
public class ContextResolverUtil {

    public static <T extends Payload> T doResolveContext(JwtTokenProvider jwtTokenProvider, Class<T> parameterType, String name, String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            /*throw ServiceException.build(CommonExceptionConstants.UNLOGIN_ERROR);*/
        }

        if (parameterType.isPrimitive() && !parameterType.equals(String.class)) {
            return null;
        }

        try {
            //对称解密
            if (parameterType.equals(String.class)) {

                Jws<Claims> claimsJws = jwtTokenProvider.parseJwtToken(jwtToken);
                return (T) claimsJws.getBody().get(name);
            } else {
                return jwtTokenProvider.parseJwtToken(jwtToken, parameterType);
            }
        } catch (Exception e) {
            /*throw ServiceException.build(CommonExceptionConstants.TOKEN_NOT_VALID);*/
            throw new RuntimeException();
        }
    }
}
