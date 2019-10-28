package com.example.demo01.service;

import com.example.demo01.model.AdminLoginRequest;

/**
 * @author wxd
 * @date 2019/10/23 14:14
 */
public interface SessionService {

    String createSession( AdminLoginRequest adminLoginRequest);
}
