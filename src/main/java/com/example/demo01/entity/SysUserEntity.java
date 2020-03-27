package com.example.demo01.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxd
 * @date 2019/10/23 14:29
 */
@Data
@Entity
@FieldNameConstants
@Table(name = "t_user")
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = -4352868070794165001L;


    /**
     * 用户 ID
     */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

}
