package com.example.demo01.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/***
 * 异常堆栈输出
 * @author xiongchuang
 * @date 2018-01-15
 */
@Component
@ConfigurationProperties(prefix="exception.stack.trace")
@Data
public class ExceptionStackTraceConfig {
    /**
     * 输出的包名前缀
     */
    private Set<String> includePackages;
    /**
     * 排除输出中的一部分包
     */
    private Set<String> excludePackages;
}
