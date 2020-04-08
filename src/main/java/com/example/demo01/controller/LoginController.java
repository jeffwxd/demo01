package com.example.demo01.controller;

import com.example.demo01.context.AdminSessionContext;
import com.example.demo01.entity.SysUserEntity;
import com.example.demo01.model.AdminLoginRequest;
import com.example.demo01.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wxd
 * @date 2019/10/23 14:07
 */
@RestController
@Api("用户登录接口")
@RequestMapping("/user")
public class LoginController {

    private final SessionService sessionService;

    public LoginController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    @ApiOperation("用户登录")
    public String createSession(
                                @Valid @RequestBody AdminLoginRequest adminLoginRequest) {
        return sessionService.createSession(adminLoginRequest);

    }
    @PostMapping("/view")
    @ApiOperation("用户列表")
    //@RequiresPermissions("user:view")
    public String  getList(AdminSessionContext adminSessionContext){
        return adminSessionContext.getUserName();
    }

}
