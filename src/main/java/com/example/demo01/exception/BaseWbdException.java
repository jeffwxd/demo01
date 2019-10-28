package com.example.demo01.exception;

import java.util.HashMap;
import java.util.Map;

/***
 *
 * @author xiongchuang
 * @date 2018-01-15
 */
public interface BaseWbdException {
    String getExceptionName();
    long getCode();
    String getMessage();
    String getExceptionTransmit();

    default Map<String, Object> getErrorMap() {
        return getErrorMap(null);
    }
    default Map<String, Object> getErrorMap(String serverName){
        Map<String, Object> map = new HashMap<>(10);

        String exceptionTransmit = this.getExceptionTransmit();
        if(exceptionTransmit !=null) {
            map.put("exceptionTransmit", exceptionTransmit);
        }
        if(serverName!=null&&serverName.trim().length()>0){
            map.put("exceptionTransmit", (exceptionTransmit==null?"":exceptionTransmit)+serverName+"->");
        }
        map.put("exceptionName", this.getExceptionName());
        map.put("code", this.getCode());
        map.put("message", this.getMessage());
        return map;
    }
}
