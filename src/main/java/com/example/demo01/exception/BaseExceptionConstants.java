/*
 *  Copyright (c) 2015.  meicanyun.com Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  meicanyun Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with meicanyun.com.
 */

package com.example.demo01.exception;

/**
 * @author xiong
 */
public interface BaseExceptionConstants {


    /**
     * 获取错误code
     *
     * @return
     */
    long getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMessage();

    /**
     * 构建一个异常
     *
     * @return
     */
    default ServiceException build() {
        return ServiceException.build(this);
    }

    /**
     * 构建一个异常 带异常链
     *
     * @param cause
     * @return
     */
    default ServiceException build(Throwable cause) {
        return ServiceException.build(this, cause);
    }

    /**
     * 比较
     *
     * @param code
     * @return
     */
    default boolean equals(Long code) {
        return code != null && code == this.getCode() ? Boolean.TRUE : Boolean.FALSE;
    }
}
