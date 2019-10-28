package com.example.demo01.context;

import com.example.demo01.exception.CommonExceptionConstants;
import com.example.demo01.jwt.provider.JwtTokenProvider;
import com.example.demo01.resolvers.AbstractContextResolver;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AdminSessionContextArgumentResolver extends AbstractContextResolver {
    private static final String TOKEN_HEADER = "Authorization";

   // private JwtTokenProvider jwtTokenProvider;

    public AdminSessionContextArgumentResolver(JwtTokenProvider jwtTokenProvider) {

        super(jwtTokenProvider);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
      return parameter.getParameterType().equals(AdminSessionContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return this.getBuyerUserContext(request);
    }

    protected AdminSessionContext getBuyerUserContext(HttpServletRequest request) {

        String jwtToken = StringUtils.defaultIfEmpty(request.getHeader(TOKEN_HEADER), request.getParameter(TOKEN_HEADER));
        AdminSessionContext adminSessionContext = doResolveContext(AdminSessionContext.class, AdminSessionContext.CONTEXT_NAME, jwtToken);
        if (adminSessionContext == null) {
            throw CommonExceptionConstants.NO_DATA_PERMISSION.build();
        }
        return adminSessionContext;
    }
}
