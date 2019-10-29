package com.example.demo01.controller;

import com.example.demo01.model.AdminLoginRequest;
import com.example.demo01.service.SessionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wxd
 * @date 2019/10/23 14:07
 */
@RestController
@Api("用户登录接口")
@RequestMapping("/login")
public class LoginController {

    private final SessionService sessionService;

    public LoginController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public String createSession(
                                @Valid @RequestBody AdminLoginRequest adminLoginRequest) {
        return sessionService.createSession(adminLoginRequest);

    }

}
