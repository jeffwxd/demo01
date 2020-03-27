package com.example.demo01.dao;

import com.example.demo01.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author wxd
 * @date 2019/10/23 14:32
 */
@Repository
public interface SysUserResponsitory  extends JpaRepository<SysUserEntity,Long>,
        JpaSpecificationExecutor<SysUserEntity> {
    /**
     * 用户登录认证
     * @return
     */
    SysUserEntity findSysUserEntityByUserNameAndPassword(String userName,String password);


}
