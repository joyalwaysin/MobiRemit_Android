package com.nagainfo.mobiremit.model;

/**
 * Created by Joy on 01/06/2017.
 */
public class UserModel {

    private int id;
    private String cust_code;
    private String cust_name;
    private String password;
    private String mpin;
    private String country_code;
    private String mobile_no;
    private String currency_code1;
    private String currency_code2;
    private String currency_code3;
    private String is_first_login;
    private String is_active_user;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCustCode() {
        return cust_code;
    }
    public void setCustCode(String cust_code) {
        this.cust_code = cust_code;
    }

    public String getName() {
        return cust_name;
    }
    public void setName(String name) {
        this.cust_name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMpin() {
        return mpin;
    }
    public void setMpin(String mpin) {
        this.mpin = mpin;
    }

    public String getCountry_code() {
        return country_code;
    }
    public void setCountry_code(String ccd) {
        this.country_code = ccd;
    }

    public String getMobile_no() {
        return mobile_no;
    }
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getCurrency_code1() {
        return currency_code1;
    }
    public void setCurrency_code1(String currency_code1) {
        this.currency_code1 = currency_code1;
    }

    public String getCurrency_code2() {
        return currency_code2;
    }
    public void setCurrency_code2(String currency_code2) {
        this.currency_code2 = currency_code2;
    }

    public String getCurrency_code3() {
        return currency_code3;
    }
    public void setCurrency_code3(String currency_code3) {
        this.currency_code3 = currency_code3;
    }

    public String getIs_first_login() {
        return is_first_login;
    }
    public void setIs_first_login(String is_first_login) {
        this.is_first_login = is_first_login;
    }

    public String getIs_active_user() {
        return is_active_user;
    }
    public void setIs_active_user(String is_active_user) {
        this.is_active_user = is_active_user;
    }
}
