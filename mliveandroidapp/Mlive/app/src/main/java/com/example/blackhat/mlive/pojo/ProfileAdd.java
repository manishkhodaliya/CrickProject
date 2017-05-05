package com.example.blackhat.mlive.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by OM on 5/5/2017.
 */

public class ProfileAdd {

    String confirmation,code,message,profileid;


    public String getConfirmation() {
        return confirmation;
    }

    @JsonProperty("Confirmation")
    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProfileid() {
        return profileid;
    }

    @JsonProperty("Profileid")
    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }
}
