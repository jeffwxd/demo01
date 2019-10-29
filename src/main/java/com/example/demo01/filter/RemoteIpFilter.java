package com.example.demo01.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取ip
 *
 * @author yangsui
 */

@Order(1)
@WebFilter(filterName = "RemoteIpFilter", urlPatterns = "/*")
@Component
public class RemoteIpFilter extends OncePerRequestFilter {

    public static final String REMOTE_IP = "remoteIp";
    public static final String X_FORWARDED_FOR =
            "x-forwarded-for";
    public static final String UNKNOWN = "unknown";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute(REMOTE_IP, getRemoteHost(request));
        request.setAttribute(X_FORWARDED_FOR, StringUtils.defaultIfBlank(request.getHeader("x-forwarded-for"), request.getHeader(REMOTE_IP)));
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me,Authorization");
        filterChain.doFilter(request, response);
    }

    public static String getRemoteHost(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }


        ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;

        if (ip != null && ip.contains(",")) {
            ip = StringUtils.split(ip, ',')[0].trim();
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = "";
        }

        return ip;
    }

}
