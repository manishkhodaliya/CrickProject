package com.example.blackhat.mlive.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by DigitalDesk on 3/26/2017.
 */

public class User  extends BasePojo implements Serializable{

    private int userid;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String contactno;
    private String emailid;
    private String show_index;
    private String showCNXnifty;
    private String market_color_theme;
    private String contract_color_theme;
    private String display_format;
    private int numberofscriptperpage;
    private String dcell1;
    private String dcell2;
    private String dcell3;
    private String dcell4;
    private String dcell5;
    private String dcell6;
    private int fontsize;
    private String city;
    private String ExpiryDate;
    private String Subscription;
    private String status;
    private String login_status;

    public String getShow_index() {
        return show_index;
    }

    public void setShow_index(String show_index) {
        this.show_index = show_index;
    }

    public String getShowCNXnifty() {
        return showCNXnifty;
    }

    public void setShowCNXnifty(String showCNXnifty) {
        this.showCNXnifty = showCNXnifty;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @JsonProperty("expiryDate")
    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }
    @JsonProperty("subscription")
    public String getSubscription() {
        return Subscription;
    }

    public void setSubscription(String subscription) {
        Subscription = subscription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMarket_color_theme() {
        return market_color_theme;
    }

    public void setMarket_color_theme(String market_color_theme) {
        this.market_color_theme = market_color_theme;
    }

    public String getContract_color_theme() {
        return contract_color_theme;
    }

    public void setContract_color_theme(String contract_color_theme) {
        this.contract_color_theme = contract_color_theme;
    }

    public String getDisplay_format() {
        return display_format;
    }

    public void setDisplay_format(String display_format) {
        this.display_format = display_format;
    }

    public int getNumberofscriptperpage() {
        return numberofscriptperpage;
    }

    public void setNumberofscriptperpage(int numberofscriptperpage) {
        this.numberofscriptperpage = numberofscriptperpage;
    }

    public String getDcell1() {
        return dcell1;
    }

    public void setDcell1(String dcell1) {
        this.dcell1 = dcell1;
    }

    public String getDcell2() {
        return dcell2;
    }

    public void setDcell2(String dcell2) {
        this.dcell2 = dcell2;
    }

    public String getDcell3() {
        return dcell3;
    }

    public void setDcell3(String dcell3) {
        this.dcell3 = dcell3;
    }

    public String getDcell4() {
        return dcell4;
    }

    public void setDcell4(String dcell4) {
        this.dcell4 = dcell4;
    }

    public String getDcell5() {
        return dcell5;
    }

    public void setDcell5(String dcell5) {
        this.dcell5 = dcell5;
    }

    public String getDcell6() {
        return dcell6;
    }

    public void setDcell6(String dcell6) {
        this.dcell6 = dcell6;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }
}
