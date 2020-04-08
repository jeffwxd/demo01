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

package com.example.demo01.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户下载文件 返回对象
 *
 * MediaType.IMAGE_JPEG_VALUE
 *
 * @author freeway
 * @date 15/12/4
 */

@Getter
@Setter
@Accessors(chain=true)
@ToString
public class Download implements Serializable {

    public static final String EXCEL="application/-excel;charset=ISO8859-1";
    private static final String DEF="application/octet-stream";

    private byte[] results;
    private String name;
    /**
     *  MediaType.IMAGE_JPEG_VALUE
     */
    private String contentType=DEF;

    public static  Download buildExcel() {
        return new Download().setContentType(EXCEL);
    }

    public static  Download build() {
        return new Download();
    }
    public static  Download build(byte[] results) {
        return new Download().setResults(results);
    }
    public static  Download build(String name,byte[] results) {
        return new Download().setName(name).setResults(results);
    }
    public static  Download build(String name,byte[] results,String contentType) {
        return new Download().setName(name).setResults(results).setContentType(contentType);
    }


}
