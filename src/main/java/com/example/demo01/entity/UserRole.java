package com.example.demo01.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author wxd
 */
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = 2354394771912648574L;

    @Id
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
