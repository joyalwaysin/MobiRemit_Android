package com.nagainfo.mobiremit.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 07/06/17.
 */

public class LoginResultItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("CurrencyCode1")
    @Expose
    private String currencyCode1;
    @SerializedName("CurrencyCode2")
    @Expose
    private String currencyCode2;
    @SerializedName("CurrencyCode3")
    @Expose
    private String currencyCode3;
    @SerializedName("IsFirstLogin")
    @Expose
    private String isFirstLogin;
    @SerializedName("LockOutTime")
    @Expose
    private String lockOutTime;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode1() {
        return currencyCode1;
    }

    public void setCurrencyCode1(String currencyCode1) {
        this.currencyCode1 = currencyCode1;
    }

    public String getCurrencyCode2() {
        return currencyCode2;
    }

    public void setCurrencyCode2(String currencyCode2) {
        this.currencyCode2 = currencyCode2;
    }

    public String getCurrencyCode3() {
        return currencyCode3;
    }

    public void setCurrencyCode3(String currencyCode3) {
        this.currencyCode3 = currencyCode3;
    }

    public String getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(String isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getLockOutTime() {
        return lockOutTime;
    }

    public void setLockOutTime(String lockOutTime) {
        this.lockOutTime = lockOutTime;
    }
}
