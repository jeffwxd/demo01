package com.example.demo01.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxd
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = -4493960686192269860L;
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

}
