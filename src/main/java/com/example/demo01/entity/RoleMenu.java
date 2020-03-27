package com.example.demo01.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author wxd
 */
@Data
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -5200596408874170216L;

    @Id
    private Long Id;
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单/按钮ID
     */
    private Long menuId;


}
