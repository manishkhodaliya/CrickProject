package com.example.blackhat.mlive.model;

/**
 * Created by SonyVaio on 3/13/2017.
 */

public class RateModel {

    String scriptName;
    String rate1,rate2,rate3,rate4,rate5,rate6;
    String n;

    public RateModel()
    {
    }

    /*public RateModel(String scriptName,int rate1,int rate2,int rate3,int rate4,int rate5,int rate6)
    {

    }*/

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getRate1() {
        return rate1;
    }

    public void setRate1(String rate1) {
        this.rate1 = rate1;
    }

    public String getRate2() {
        return rate2;
    }

    public void setRate2(String rate2) {
        this.rate2 = rate2;
    }

    public String getRate3() {
        return rate3;
    }

    public void setRate3(String rate3) {
        this.rate3 = rate3;
    }

    public String getRate4() {
        return rate4;
    }

    public void setRate4(String rate4) {
        this.rate4 = rate4;
    }

    public String getRate5() {
        return rate5;
    }

    public void setRate5(String rate5) {
        this.rate5 = rate5;
    }

    public String getRate6() {
        return rate6;
    }

    public void setRate6(String rate6) {
        this.rate6 = rate6;
    }

    public boolean equals(Object o){
        if(o instanceof RateModel){
            RateModel toCompare = (RateModel) o;
            return this.getScriptName().equals(toCompare.getScriptName());
        }
        return false;
    }


}
