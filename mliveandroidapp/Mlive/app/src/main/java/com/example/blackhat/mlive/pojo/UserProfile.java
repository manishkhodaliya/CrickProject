package com.example.blackhat.mlive.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DigitalDesk on 4/2/2017.
 */

public class UserProfile extends BasePojo{



    String userid,username, password, firstname, lastname, contactno, emailid, status, dcell2,dcell3,dcell4,dcell5,dcell6,
            city,ExpiryDate,message,Subscription,fontsize;



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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("Subscription")
    public String getSubscription() {
        return Subscription;
    }

    public void setSubscription(String subscription) {
        Subscription = subscription;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }
}