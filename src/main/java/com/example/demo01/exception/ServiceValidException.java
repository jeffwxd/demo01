package com.example.demo01.exception;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 服务校验异常类
 *
 * @author freeway
 * @date 15/12/17
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ServiceValidException extends ServiceException {

    private static final long serialVersionUID = 1027421157779490101L;

    public static final String RESULT = "result";
    protected long code;
    protected String message;
    private List<ErrorItem> errorItems;
    public static final String EXCEPTION_NAME="ServiceValidException";

    public ServiceValidException(String message) {
        super( CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode(),message);
        this.code = CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode();
        this.message= CommonExceptionConstants.NOT_AVAILABLE_DATA.getMessage();
    }
    public ServiceValidException(String message, String exceptionTransmit) {
        super(CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode(),message);
        this.code = CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode();
        this.message=message;
        this.exceptionTransmit = exceptionTransmit;
    }
    public ServiceValidException(String message, Throwable cause) {
        super( CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode(),message, cause);
        this.code = CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode();
        this.message= CommonExceptionConstants.NOT_AVAILABLE_DATA.getMessage();
    }
    public ServiceValidException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(CommonExceptionConstants.NOT_AVAILABLE_DATA);
        this.code = CommonExceptionConstants.NOT_AVAILABLE_DATA.getCode();
        this.message= CommonExceptionConstants.NOT_AVAILABLE_DATA.getMessage();

        if ( constraintViolations != null &&  !constraintViolations.isEmpty()) {

            for (ConstraintViolation constraintViolation : constraintViolations) {
                final Path.Node leafNode = getLeafNode(constraintViolation);
                final ElementKind elementKind = leafNode.getKind();
                if (elementKind == ElementKind.PARAMETER) {
                    addErrorItem(getParameterName(constraintViolation), constraintViolation.getMessage());
                } else if (elementKind == ElementKind.PROPERTY) {
                    Path.PropertyNode propertyNode = leafNode.as(Path.PropertyNode.class);
                    addErrorItem(propertyNode.getName(), constraintViolation.getMessage());
                } else if (elementKind == ElementKind.RETURN_VALUE) {
                    addErrorItem(RESULT, constraintViolation.getMessage());
                }
            }
        }
    }


    public static ServiceValidException build(String message) {
        return new ServiceValidException(message);
    }
    public static ServiceValidException build(String message, Throwable cause) {
        return new ServiceValidException(message, cause);
    }

    public static ServiceValidException build(Set<? extends ConstraintViolation<?>> constraintViolations) {
        return new ServiceValidException(constraintViolations);
    }


    private List<String> getParameterNames(Class<?> clazz, String methodName) {
        ClassPool pool = ClassPool.getDefault();
        List<String> params = new ArrayList<>();
        try {
            CtClass ctClass = pool.get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);

            // 使用javassist的反射方法的参数名
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int len = ctMethod.getParameterTypes().length;
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 1 : 2;
                for (int i = 0; i < len; i++) {
                    params.add(attr.variableName(i + pos));
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return params;
    }

    private String getParameterName(ConstraintViolation constraintViolation) {

        Class<?> clazz = constraintViolation.getRootBeanClass();

        final Path.Node leafNode = getLeafNode(constraintViolation);

        int index = leafNode.as(Path.ParameterNode.class).getParameterIndex();
        String methodName = getMethodNode(constraintViolation).getName();

        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);

            // 使用javassist的反射方法的参数名
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                return attr.variableName(index + pos);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Path.Node getLeafNode(final ConstraintViolation<?> constraintViolation) {
        final Iterator<Path.Node> nodes = constraintViolation.getPropertyPath().iterator();
        Path.Node leafNode = null;
        while (nodes.hasNext()) {
            leafNode = nodes.next();
        }
        return leafNode;
    }

    private Path.Node getMethodNode(final ConstraintViolation<?> constraintViolation) {
        final Iterator<Path.Node> nodes = constraintViolation.getPropertyPath().iterator();
        Path.Node nextNode = null;
        while (nodes.hasNext()) {
            nextNode = nodes.next();
            if (ElementKind.METHOD == nextNode.getKind()) {
                return nextNode;
            }
        }
        return nextNode;
    }


    public ServiceValidException addErrorItem(String field, String message) {
        ErrorItem errorItem = new ErrorItem(field, message);
        if (this.errorItems == null) {
            this.errorItems = new ArrayList<ErrorItem>();
        }
        errorItems.add(errorItem);
        return this;
    }

    @Getter
    @Setter
    @ToString
    public static class ErrorItem implements Serializable {

        private String field;
        private String message;

        public ErrorItem() {
        }

        public ErrorItem(String field, String message) {
            this.field = field;
            this.message = message;
        }

    }

    @Override
    public String getExceptionName() {
        return EXCEPTION_NAME;
    }

    @Override
    public Map<String, Object> getErrorMap(String serverName) {
        Map<String, Object> map = super.getErrorMap(serverName);
        map.put("exceptionName", EXCEPTION_NAME);
        map.put("errorItems",this.getErrorItems());
        map.put("message",this.message);
        return map;
    }

}
