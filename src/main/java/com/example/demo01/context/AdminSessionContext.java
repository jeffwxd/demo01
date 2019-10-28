package com.example.demo01.context;

import com.example.demo01.jwt.provider.Payload;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminSessionContext implements Payload, Serializable {

    @ApiModelProperty(hidden = true)
    private String userName;

    public static final String CONTEXT_NAME = "admin_session_context";

    @Override
    public String key() {
        return CONTEXT_NAME;
    }
}
