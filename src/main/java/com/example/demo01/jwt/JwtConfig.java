package com.example.demo01.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wxd
 * @date 2019/10/15 10:20
 */
@Component
@ConfigurationProperties(prefix="wbdjwt.admin")
public class JwtConfig {

    /**
     *对称加密密钥
     */

    private String secret;

    /**
     *签名发放者
     */

    private String issuer;
    /**
     *签名面向的用户
     */

    private String subject;

    /**
     *签名有效期，单位毫秒
     */

    private Long ttlMillis;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getTtlMillis() {
        return ttlMillis;
    }

    public void setTtlMillis(Long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }
}
