package com.example.demo01.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wxd
 * @date 2019/10/23 14:13
 */
@Data
@Api("用户登录信息")
public class AdminLoginRequest {

    @ApiModelProperty(value = "用户名称",required = true)
    private String userName;

    @ApiModelProperty(value = "用户密码",required = true)
    private String password;
}
