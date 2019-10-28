package com.example.demo01.context;

import com.example.demo01.jwt.JwtConfig;
import com.example.demo01.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtConfig jwtConfig;

    @Bean
    public JwtTokenProvider createProvider() {
        return new JwtTokenProvider(jwtConfig);
    }

    @Bean
    public AdminSessionContextArgumentResolver adminSessionContextArgumentResolver() {
        return new AdminSessionContextArgumentResolver(createProvider());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(adminSessionContextArgumentResolver());
    }
}

