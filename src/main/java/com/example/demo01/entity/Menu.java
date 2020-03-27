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
public class Menu implements Serializable {

    private static final long serialVersionUID = 8571011372410167901L;


    /**
     * 菜单/按钮ID
     */
    private Long menuId;

    /**
     * 上级菜单ID
     */
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    private String menuName;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 权限标识
     */

    private String perms;



}
