package com.example.blackhat.mlive.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DigitalDesk on 4/2/2017.
 */

public class Index {

    String scriptname,Itm_Value6,Itm_Value19;


    public String getScriptname() {
        return scriptname;
    }

    public void setScriptname(String scriptname) {
        this.scriptname = scriptname;
    }

    @JsonProperty("Itm_Value6")
    public String getItm_Value6() {
        return Itm_Value6;
    }

    public void setItm_Value6(String itm_Value6) {
        Itm_Value6 = itm_Value6;
    }
    @JsonProperty("Itm_Value19")
    public String getItm_Value19() {
        return Itm_Value19;
    }

    public void setItm_Value19(String itm_Value19) {
        Itm_Value19 = itm_Value19;
    }
}
