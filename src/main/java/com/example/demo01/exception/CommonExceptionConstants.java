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
 *
 * @author xiong
 * @date 2015/12/23
 */
public enum CommonExceptionConstants implements BaseExceptionConstants {
    //
    NOT_AVAILABLE_DATA(0,"无效的传入参数"),
    NO_FIND_DATA(1,"数据不存在"),
    UNLOGIN_ERROR(2, "需要登录后才能访问"),
    NO_DATA_PERMISSION(3,"没有数据操作权限"),
    // 上传图片的一些异常
    READ_FILE_ERROR(4,"读取文件错误"),
    BEYOND_IMAGE_MAX_SIZE(5,"超过文件最大限制"),
    NOT_IMAGE(6,"此文件不是图片文件"),
    FILE_NOT_SUPPORT(7,"文件格式不支持"),
    IP_LOCAL_ERROR(8,"获取本机IP失败"),
    NO_JURISDICTION(9,"没有权限操作"),
    NO_SYS_ADDRESS_INFO(10,"没有找到地址编码信息"),

    UPLOAD_IMAGE_FAILED(12,"图片文件上传失败"),
    MOBILE_NUMBER_FORMAT_ERROR(13, "手机号码格式错误"),
    DATE_FORMAT_ERR(14,"时间格式不正确"),
    UPLOAD_FAILED(15,"文件上传失败"),
    DOWNLOAD_FAILED(16,"文件下载失败"),

    SYSTEM_ERR(17,"系统未知错误，请稍后重试"),
    DATA_INTEGRITY(18,"数据已存在，请检查数据"),
    TOKEN_EXPIRED(19, "token已经过期"),
    TOKEN_NOT_VALID(20, "token验证错误"),
    SYSTEM_SERVER_ERR(21,"系统服务繁忙，请稍后重试"),
    DATA_VERSION(22,"数据修改失败，请稍后重试"),
    VALIDATION_SIGN(23, "签名错误"),
    VALIDATION_TIMESTAMP(24, "时间戳错误"),

    validation_code_error(50, "验证码输入错误"),
    validation_code_send_too_frequently(51, "验证码发送太过频繁,请稍后重试"),
    validation_code_has_been_locked(52, "账号已锁定,请稍后重试"),
    validation_code_not_exist(53, "验证码不正确"),
    validation_code_has_expired(54, "验证码已失效"),
    validation_code_input_incorrect(55, "错误次数过多，请1个小时后再次尝试"),

    TUISONG_DEVICE_TYPE_ERROR(56,"设备类型错误"),

    OPTIMISTIC_LOCKING_FAILURE(61,"系统服务繁忙，请稍后重试"),
    DATA_INTEGRITY_VIOLATION(62,"系统服务繁忙，请稍后重试"),

    WEIXIN_GET_TOKEN_ERROR(100, "获取微信公众号access_token异常"),
    WEIXIN_GET_TICKET_ERROR(101, "获取微信公众号jsapi_ticket异常")




    ;

    private long code;
    private String message;

    CommonExceptionConstants(long code, String message) {
        this.code = 10000 +code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
