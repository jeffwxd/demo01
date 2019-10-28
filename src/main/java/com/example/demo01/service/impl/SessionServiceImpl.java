package com.example.demo01.service.impl;

import com.example.demo01.context.AdminSessionContext;
import com.example.demo01.dao.SysUserResponsitory;
import com.example.demo01.entity.SysUserEntity;
import com.example.demo01.exception.CommonExceptionConstants;
import com.example.demo01.jwt.JwtConfig;
import com.example.demo01.jwt.provider.JwtTokenProvider;
import com.example.demo01.model.AdminLoginRequest;
import com.example.demo01.service.SessionService;
import com.example.demo01.utils.DigestUtil;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

/**
 * @author wxd
 * @date 2019/10/23 14:15
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SysUserResponsitory sysUserResponsitory;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String createSession(AdminLoginRequest adminLoginRequest) {

        // String md5Pwd = DigestUtil.md5(adminLoginRequest.getPassword(), DigestUtil.Charset.UFT_8);
        System.out.println(adminLoginRequest.getPassword());
        String password = DigestUtils.md5DigestAsHex(adminLoginRequest.getPassword().getBytes());
        SysUserEntity entity = sysUserResponsitory.findSysUserEntityByUserNameAndPasswordAndStateTrue(adminLoginRequest.getUserName(), password);
         entity.getUserName();
        if (entity == null) {
            throw CommonExceptionConstants.NO_FIND_DATA.build();
            //Assert.notNull(entity,"用户信息登录错误");
        }
        AdminSessionContext adminSessionContext = new AdminSessionContext();
        adminSessionContext.setUserName(entity.getUserName());
        //JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtConfig);

        return jwtTokenProvider.createJwtToken(adminSessionContext);
    }
}
