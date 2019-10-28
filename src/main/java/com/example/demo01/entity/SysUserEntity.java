package com.example.demo01.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

/**
 * @author wxd
 * @date 2019/10/23 14:29
 */
@Data
@Entity
@FieldNameConstants
@Table(name = "sys_user")
public class SysUserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;

    @Column(name = "create_time")
    private String createdTime;

    @Column(name = "update_time")
    private String updateTime;

    private Boolean state;
}
