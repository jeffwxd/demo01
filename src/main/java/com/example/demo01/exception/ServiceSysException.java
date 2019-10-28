package com.example.demo01.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 服务系统异常
 *
 * @author freeway
 * @date 15/12/4
 */
@Getter
@Setter
@Accessors(chain=true)
@ToString
public class ServiceSysException extends RuntimeException implements BaseWbdException {


    private long code;

    protected String message;

    public static final String EXCEPTION_NAME="ServiceSysException";

    private String exceptionTransmit;

    public ServiceSysException(long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceSysException(long code, String message, String exceptionTransmit) {
        super(message);
        this.code = code;
        this.message = message;
        this.exceptionTransmit = exceptionTransmit;
    }

    public ServiceSysException(long code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public ServiceSysException(BaseExceptionConstants baseExceptionConstant) {
        super(baseExceptionConstant.getMessage());
        this.code = baseExceptionConstant.getCode();
        this.message = baseExceptionConstant.getMessage();
    }



    public static ServiceSysException build(long code, String message) {
        return new ServiceSysException(code, message);
    }
    public static ServiceSysException build(long code, String message, Throwable cause) {
        return new ServiceSysException(code, message, cause);
    }

    public static ServiceSysException build(String message, Throwable cause) {
        return build(CommonExceptionConstants.SYSTEM_ERR.getCode(), message, cause);
    }

    public static ServiceSysException build(Throwable cause) {
        return build(CommonExceptionConstants.SYSTEM_ERR.getCode(), CommonExceptionConstants.SYSTEM_ERR.getMessage(), cause);
    }
    public static ServiceSysException build(BaseExceptionConstants baseExceptionConstants) {
        return build(baseExceptionConstants.getCode(), baseExceptionConstants.getMessage());
    }

    @Override
    public String getExceptionName() {
        return EXCEPTION_NAME;
    }


}