package com.example.demo01.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 服务业务异常
 *
 * @author freeway
 * @date 15/12/4
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class  ServiceException extends RuntimeException implements BaseWbdException {

    protected long code;

    protected String message;

    public static final String EXCEPTION_NAME="ServiceException";

    protected String exceptionTransmit;


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(long code, String message,String exceptionTransmit) {
        super(message);
        this.code = code;
        this.message = message;
        this.exceptionTransmit = exceptionTransmit;
    }

    public ServiceException(long code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    public ServiceException(BaseExceptionConstants baseExceptionConstant) {
        super(baseExceptionConstant.getMessage());
        this.code = baseExceptionConstant.getCode();
        this.message = baseExceptionConstant.getMessage();
    }

    public ServiceException(BaseExceptionConstants baseExceptionConstant, Throwable cause) {
        super(baseExceptionConstant.getMessage(), cause);
        this.code = baseExceptionConstant.getCode();
        this.message = baseExceptionConstant.getMessage();
    }



    public static ServiceException build(long code, String message) {
        return new ServiceException(code, message);
    }

    public static ServiceException build(long code, String message, Throwable cause) {
        return new ServiceException(code, message, cause);
    }
    public static ServiceException build(BaseExceptionConstants baseExceptionConst) {
        return new ServiceException(baseExceptionConst);
    }

    public static ServiceException build(BaseExceptionConstants baseExceptionConstant, Throwable cause) {
        return new ServiceException(baseExceptionConstant, cause);
    }


    @Override
    public String getExceptionName() {
        return EXCEPTION_NAME;
    }






}
