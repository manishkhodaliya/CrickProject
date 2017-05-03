/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.blackhat.mlive.pojo;

import com.example.blackhat.mlive.pojo.BasePojo;
import java.util.List;
import java.util.Map;

/**
 * The <code>CallResponse</code> class is responsible for in <b>CCM</b>.
 * @author Manish
 */
public class CallResponse extends BasePojo{
    
    private int code;
    private Map<String ,Object> data;
    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
