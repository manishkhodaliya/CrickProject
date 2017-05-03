package com.example.blackhat.mlive.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by SonyVaio on 3/13/2017.
 */


public class MkW implements Serializable{

    private String sn,E,s,N;
    @JsonProperty
    private String d1,d2,d3,d4,d5,d6;

    private int totalrow;

    public String getN() {
        return N;
    }
    @JsonProperty("N")
    public void setN(String n) {
        N = n;
    }

    public String getSn() {
        return sn;
    }
    @JsonProperty("sn")
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getE() {
        return E;
    }
    @JsonProperty("E")
    public void setE(String e) {
        E = e;
    }

    public String getS() {
        return s;
    }
    @JsonProperty("S")
    public void setS(String s) {
        s = s;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getD6() {
        return d6;
    }

    public void setD6(String d6) {
        this.d6 = d6;
    }

    public int getTotalrow() {
        return totalrow;
    }
    @JsonIgnoreProperties
    public void setTotalrow(int totalrow) {
        this.totalrow = totalrow;
    }
}
