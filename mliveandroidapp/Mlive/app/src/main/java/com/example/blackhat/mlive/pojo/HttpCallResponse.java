package com.example.blackhat.mlive.pojo;

import java.util.Map;

/**
 * Created by BlackHat on 13/04/2017.
 */
public class HttpCallResponse  {
    private Map< String, Object> data;
    private  int code;
    private String messages;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
