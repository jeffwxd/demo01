package com.example.demo01.exception;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 异常拦截
 *
 * @author yangsui
 * @date 2017/4/7
 */

@RestControllerAdvice
public class DefaultGlobalExceptionHandler {
    private static final String SHOW_LOGGER_EXCEPTION_FLAG_TRUE = "true";
    private static final String SHOW_LOGGER_EXCEPTION_FLAG_FALSE = "false";

    public static final String LOGGER_NOHANDLER_FOUND_EXCEPTION = "LOGGER_NOHANDLER_FOUND_EXCEPTION";
    public static final String LOGGER_PARAMETER_EXCEPTION = "LOGGER_PARAMETER_EXCEPTION";
    public static final String LOGGER_SERVICE_EXCEPTION = "LOGGER_SERVICE_EXCEPTION";


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGlobalExceptionHandler.class);
    protected static final Logger GLOBAL_EXCEPTION_LOGGER = LoggerFactory.getLogger("WBD_GLOBAL_EXCEPTION");
    protected static final Set<String> INCLUDE_PACKAGES = new HashSet<String>() {
        {
            add("com.iwubida");
        }
    };

    protected static final Set<String> EXCLUDE_PACKAGES = new HashSet<String>() {
        {
            add("com.iwubida.springboot.web.exceptions");
            add("com.iwubida.base.exceptions");
        }
    };

    @Autowired
    private Environment env;
    @Autowired
    private ExceptionStackTraceConfig exceptionStackTraceConfig;

    private String applicationName;

    private Set<String> activeProfiles;

  /*  @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }*/


    protected boolean showLoggerException(String key) {
        try {
            String property = System.getProperty(key);
            if (property == null) {
                System.setProperty(key, SHOW_LOGGER_EXCEPTION_FLAG_FALSE);
            }
            return SHOW_LOGGER_EXCEPTION_FLAG_TRUE.equals(property);
        } catch (Exception e) {
        }

        return Boolean.FALSE;
    }

    protected Boolean activeProfilesHas(String... actives) {
        if (actives == null) {
            return Boolean.FALSE;
        }

        if (activeProfiles == null) {
            activeProfiles = new HashSet<String>();

            for (String activeProfile : env.getActiveProfiles()) {
                activeProfiles.add(activeProfile);
            }
        }

        for (String active : actives) {
            if (activeProfiles.contains(active)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    protected String getApplicationName() {
        if (applicationName == null) {
            applicationName = env.getProperty("spring.application.name");
        }
        return applicationName;
    }


    private static final String NOHANDLERFOUNDEXCEPTION_EXCEPTION_MESSAGE = "NoHandlerFoundException: %s %s , %s";

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, Object> noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        if (this.showLoggerException(LOGGER_NOHANDLER_FOUND_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(NOHANDLERFOUNDEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        }
        return ServiceException.build(CommonExceptionConstants.NO_FIND_DATA.getCode(), CommonExceptionConstants.NO_FIND_DATA.getMessage() + "[404]").getErrorMap(this.getApplicationName());
    }


    private static final String ILLEGALARGUMENTEXCEPTION_EXCEPTION_MESSAGE = "IllegalArgumentException: %s %s , %s";

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> illegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {

        GLOBAL_EXCEPTION_LOGGER.error(String.format(ILLEGALARGUMENTEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()));

        if (this.showLoggerException(LOGGER_NOHANDLER_FOUND_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(ILLEGALARGUMENTEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        }

        return ServiceException.build(CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode(), CommonExceptionConstants.NOT_AVAILABLE_DATA.getMessage() + "[illegal]").getErrorMap(this.getApplicationName());
    }

    private static final String HTTPREQUESTMETHODNOTSUPPORTEDEXCEPTION_EXCEPTION_MESSAGE = "HttpRequestMethodNotSupportedException: %s %s , %s";

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, Object> httpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {

        GLOBAL_EXCEPTION_LOGGER.warn(String.format(HTTPREQUESTMETHODNOTSUPPORTEDEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()));

        if (this.showLoggerException(LOGGER_NOHANDLER_FOUND_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(HTTPREQUESTMETHODNOTSUPPORTEDEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        }
        return ServiceException.build(CommonExceptionConstants.NO_FIND_DATA.getCode(), CommonExceptionConstants.NO_FIND_DATA.getMessage() + "[method]").getErrorMap(this.getApplicationName());
    }


    /**
     * validation验证
     *
     * @param ex
     * @return
     */
    private static final String CONSTRAINTVIOLATIONEXCEPTION_EXCEPTION_MESSAGE = "ConstraintViolationException: %s %s , %s";

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> constraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        GLOBAL_EXCEPTION_LOGGER.error(String.format(CONSTRAINTVIOLATIONEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);

        return ServiceValidException.build(ex.getConstraintViolations()).getErrorMap(this.getApplicationName());
    }

    /**
     * validation验证
     *
     * @param ex
     * @return
     */
    private static final String BIND_EXCEPTION_MESSAGE = "BindException: %s %s , %s";

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> bindException(HttpServletRequest request, BindException ex) {


        ServiceValidException validException = ServiceValidException.build(CommonExceptionConstants.NOT_AVAILABLE_DATA.getMessage(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError allError : fieldErrors) {
            validException.addErrorItem(allError.getField(), allError.getDefaultMessage());
        }

        if (this.showLoggerException(LOGGER_PARAMETER_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(BIND_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()) + JSON.toJSONString(validException.getErrorItems()), ex);
        }

        GLOBAL_EXCEPTION_LOGGER.error(String.format(BIND_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);

        return validException.getErrorMap(this.getApplicationName());
    }

    /**
     * 开发时控制台 提醒简化
     *
     * @param ex
     * @return
     */
    private static final String CONSTRAINTDECLARATIONEXCEPTION_EXCEPTION_MESSAGE = "ConstraintDeclarationException: %s %s , 参数验证注解 不匹配，请检查 接口和实现 的 参数验证注解 是否一致 %s";

    @ExceptionHandler(ConstraintDeclarationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> constraintDeclarationException(HttpServletRequest request, ConstraintDeclarationException ex) {

        GLOBAL_EXCEPTION_LOGGER.warn(String.format(CONSTRAINTDECLARATIONEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);

        return ServiceValidException.build(CommonExceptionConstants.NOT_AVAILABLE_DATA, ex).getErrorMap(this.getApplicationName());
    }


    /**
     * 请求参数为空
     *
     * @param request
     * @param ex
     * @return
     */
    private static final String MISSINGSERVLETREQUESTPARAMETEREXCEPTION_EXCEPTION_MESSAGE = "MissingServletRequestParameterException: %s %s , %s 不能为空,类型 %s";
    private static final String MISSINGSERVLETREQUESTPARAMETEREXCEPTION_MESSAGE = "%s 不能为空,类型 %s";
    private static final String MISSINGSERVLETREQUESTPARAMETEREXCEPTION_MESSAGE_DBUG = "%s %s  , %s 不能为空,类型 %s    %s";

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> missingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        if (this.showLoggerException(LOGGER_PARAMETER_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(MISSINGSERVLETREQUESTPARAMETEREXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getParameterName(), ex.getParameterType()), ex);
        }

        LOGGER.debug(String.format(MISSINGSERVLETREQUESTPARAMETEREXCEPTION_MESSAGE_DBUG, request.getMethod(), request.getRequestURI(), ex.getParameterName(), ex.getParameterType(), this.throwableAt(ex)));

        ServiceValidException validException = ServiceValidException.build(String.format(MISSINGSERVLETREQUESTPARAMETEREXCEPTION_MESSAGE, ex.getParameterName(), ex.getParameterType()), ex);
        validException.addErrorItem(ex.getParameterName(), ex.getParameterType());

        return validException.getErrorMap(this.getApplicationName());
    }


    /**
     * 请求参数
     *
     * @param request
     * @param ex
     * @return
     */
    private static final String METHODARGUMENTNOTVALIDEXCEPTION_EXCEPTION_MESSAGE = "MethodArgumentNotValidException: %s %s , %s 参数验证错误，%s ,%s";
    private static final String METHODARGUMENTNOTVALIDEXCEPTION_MESSAGE = "%s 参数验证错误";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {

        String parameterName = ex.getParameter() != null ? ex.getParameter().getParameterName() : "";
        String parameterType = ex.getParameter() != null && ex.getParameter().getParameterType() != null ? ex.getParameter().getParameterType().getName() : "";

        GLOBAL_EXCEPTION_LOGGER.error(String.format(METHODARGUMENTNOTVALIDEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), parameterName, parameterType, ex.getMessage()), ex);

        ServiceValidException build = ServiceValidException.build(String.format(METHODARGUMENTNOTVALIDEXCEPTION_MESSAGE, parameterName), ex);

        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult != null) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (fieldErrors != null && !fieldErrors.isEmpty()) {
                for (FieldError fieldError : fieldErrors) {
                    build.addErrorItem(fieldError.getField(), fieldError.getDefaultMessage());
                }
            }
        }

        return build.getErrorMap(this.getApplicationName());
    }


    /**
     * 请求参数类型转换错误
     *
     * @param ex
     * @return
     */
    private static final String METHODARGUMENTTYPEMISMATCHEXCEPTION_EXCEPTION_MESSAGE = "MethodArgumentTypeMismatchException: %s %s , %s 类型传入错误，类型 %s";
    private static final String METHODARGUMENTTYPEMISMATCHEXCEPTION_MESSAGE = "%s 类型传入错误";

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> methodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {

        String type = ex.getRequiredType() == null ? "" : ex.getRequiredType().getName();

        GLOBAL_EXCEPTION_LOGGER.error(String.format(METHODARGUMENTTYPEMISMATCHEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getName(), type), ex);

        ServiceValidException validException = ServiceValidException.build(String.format(METHODARGUMENTTYPEMISMATCHEXCEPTION_MESSAGE, ex.getName()), ex);
        validException.addErrorItem(ex.getName(), type);

        return validException.getErrorMap(this.getApplicationName());
    }


    /**
     * 参数检查异常
     *
     * @param ex
     * @return
     */
    private static final String SERVICEVALIDEXCEPTION_EXCEPTION_MESSAGE = "ServiceValidException: %s %s , %s";
    private static final String SERVICEVALIDEXCEPTION_EXCEPTION_MESSAGE_DBUG = "ServiceValidException: %s %s , %s   %s";

    @ExceptionHandler(ServiceValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> serviceValidException(HttpServletRequest request, ServiceValidException ex) {
        if (this.showLoggerException(LOGGER_PARAMETER_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(SERVICEVALIDEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        }

        LOGGER.debug(String.format(SERVICEVALIDEXCEPTION_EXCEPTION_MESSAGE_DBUG, request.getMethod(), request.getRequestURI(), ex.getMessage(), this.throwableAt(ex)));

        return ex.getErrorMap(this.getApplicationName());
    }

    /**
     * 业务异常
     *
     * @param ex
     * @return
     */
    private static final String SERVICEEXCEPTION_EXCEPTION_MESSAGE = "ServiceException: %s %s , %s";
    private static final String SERVICEEXCEPTION_EXCEPTION_MESSAGE_DBUG = "ServiceException: %s %s , %s    %s";

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> serviceException(HttpServletRequest request, ServiceException ex) {

        if (ex instanceof ServiceValidException) {
            return serviceValidException(request, (ServiceValidException) ex);
        }

        LOGGER.debug(String.format(SERVICEEXCEPTION_EXCEPTION_MESSAGE_DBUG, request.getMethod(), request.getRequestURI(), ex.getMessage(), this.throwableAt(ex)));

        if (this.showLoggerException(LOGGER_SERVICE_EXCEPTION)) {
            GLOBAL_EXCEPTION_LOGGER.error(String.format(SERVICEEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        }
        return ex.getErrorMap(this.getApplicationName());
    }

    /**
     * 业务异常
     *
     * @param ex
     * @return
     */
    private static final String SERVICESYSEXCEPTION_EXCEPTION_MESSAGE = "ServiceSysException: %s %s , %s";

    @ExceptionHandler(ServiceSysException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> serviceSysException(HttpServletRequest request, ServiceSysException ex) {


        LOGGER.error(String.format(SERVICESYSEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);

        return ex.getErrorMap(this.getApplicationName());
    }


    /**
     * @param exception
     * @return
     */
    private static final String SQLEXCEPTION_EXCEPTION_MESSAGE = "SQLException: %s %s , %s";

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> sqlException(HttpServletRequest request, SQLException ex) {
        this.notification("sql异常", ex);

        LOGGER.error(String.format(SQLEXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        return ServiceSysException.build(CommonExceptionConstants.SYSTEM_SERVER_ERR.getCode(), CommonExceptionConstants.SYSTEM_SERVER_ERR.getMessage() + "[sql err]", ex).getErrorMap(this.getApplicationName());
    }

    /**
     * @param exception
     * @return
     */
    private static final String NULLPOINTEREXCEPTION_EXCEPTION_MESSAGE = "NullPointerException: %s %s , %s";

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> handleException(HttpServletRequest request, NullPointerException ex) {
        this.notification("空指针异常", ex);
        System.out.println("空指针到了...............");
        LOGGER.error(String.format(NULLPOINTEREXCEPTION_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        return ServiceSysException.build(CommonExceptionConstants.SYSTEM_SERVER_ERR.getCode(), CommonExceptionConstants.SYSTEM_SERVER_ERR.getMessage() + "[null err]", ex).getErrorMap(this.getApplicationName());
    }

    /**
     * 统一未知异常
     *
     * @param throwable
     * @return
     * @throws Exception
     */
    private static final String DEFAULTERRORHANDLER_EXCEPTION_MESSAGE = "defaultErrorHandler: %s %s , %s";

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> defaultErrorHandler(HttpServletRequest request, Throwable ex) {
        this.notification("未知异常", ex);

        LOGGER.error(String.format(DEFAULTERRORHANDLER_EXCEPTION_MESSAGE, request.getMethod(), request.getRequestURI(), ex.getMessage()), ex);
        return ServiceSysException.build(CommonExceptionConstants.SYSTEM_SERVER_ERR.getCode(), CommonExceptionConstants.SYSTEM_SERVER_ERR.getMessage() + "[default err]", ex).getErrorMap(this.getApplicationName());
    }

    protected void notification(String msg, Throwable ex) {
        try {
            //String exceptionMessage = StringUtils.buildExceptionMessage(ex);
            //this.notification(msg,exceptionMessage);
        } catch (Throwable e) {
            LOGGER.error("通知出错", e);
        }
    }


    protected Set<String> getIncludePackages() {
        if (CollectionUtils.isEmpty(exceptionStackTraceConfig.getIncludePackages())) {
            return INCLUDE_PACKAGES;
        }
        return exceptionStackTraceConfig.getIncludePackages();
    }

    protected boolean include(StackTraceElement element) {
        Set<String> includePackages = this.getIncludePackages();
        if (!CollectionUtils.isEmpty(includePackages)) {
            for (String includePackage : includePackages) {
                if (element.getClassName().startsWith(includePackage)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Set<String> getExcludePackages() {
        if (CollectionUtils.isEmpty(exceptionStackTraceConfig.getExcludePackages())) {
            return EXCLUDE_PACKAGES;
        }
        return exceptionStackTraceConfig.getExcludePackages();
    }

    protected boolean exclude(StackTraceElement element) {
        Set<String> excludePackages = this.getExcludePackages();

        if (!CollectionUtils.isEmpty(excludePackages)) {
            for (String includePackage : excludePackages) {
                if (element.getClassName().startsWith(includePackage)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected String throwableAt(Throwable ex) {
        if (ex == null || !this.activeProfilesHas("dev", "test")) {
            return StringUtils.EMPTY;
        }
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        if (stackTraceElements == null || stackTraceElements.length <= 0) {
            return StringUtils.EMPTY;
        }
        StackTraceElement stackTraceElement = stackTraceElements[0];
        for (StackTraceElement element : stackTraceElements) {
            if (this.include(element) && !this.exclude(element)) {
                stackTraceElement = element;
                break;
            }
        }

        return "\n    at " +
                stackTraceElement.getClassName() +
                "." +
                stackTraceElement.getMethodName() +
                "(" +
                stackTraceElement.getFileName() +
                ":" +
                stackTraceElement.getLineNumber() +
                ")";
    }
    //public abstract void notification(String msg,String exceptionMessage);


}
