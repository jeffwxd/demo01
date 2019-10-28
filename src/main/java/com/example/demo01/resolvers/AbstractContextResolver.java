package com.example.demo01.resolvers;

import com.example.demo01.jwt.provider.JwtTokenProvider;
import com.example.demo01.jwt.provider.Payload;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * Created by konghang on 2017/8/31.
 */
public abstract class AbstractContextResolver implements HandlerMethodArgumentResolver {

    protected JwtTokenProvider jwtTokenProvider;

    public AbstractContextResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    protected Object doResolveContext(MethodParameter methodParameter, String name, String jwtToken) {
        return ContextResolverUtil.doResolveContext(jwtTokenProvider,(Class<? extends Payload>) methodParameter.getParameterType(),name,jwtToken);
    }
    protected <T extends Payload> T doResolveContext(Class<T> parameterType, String name, String jwtToken) {
        return ContextResolverUtil.doResolveContext(jwtTokenProvider,parameterType,name,jwtToken);
    }
}
