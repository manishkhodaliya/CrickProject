package com.example.blackhat.mlive.pojo;

import java.io.Serializable;

/**
 * Created by DigitalDesk on 3/26/2017.
 */

public class BasePojo implements Serializable {
    private  int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

