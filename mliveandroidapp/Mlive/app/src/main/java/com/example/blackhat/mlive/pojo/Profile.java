package com.example.blackhat.mlive.pojo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by DigitalDesk on 3/29/2017.
 */

public class Profile  extends BasePojo implements Serializable{

    public String profileid;
    public String userid;
    public String profilename;
    public String status;
    public String profile_type;
    public String Count;
    LinkedHashMap<Integer,String> scripts;


    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(String profile_type) {
        this.profile_type = profile_type;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public LinkedHashMap<Integer, String> getScripts() {
        return scripts;
    }

    public void setScripts(LinkedHashMap<Integer, String> scripts) {
        this.scripts = scripts;
    }
}
