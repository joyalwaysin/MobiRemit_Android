package com.nagainfo.mobiremit.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 07/06/17.
 */

public class LoginDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("Password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginDataItem() {
    }

    /**
     *
     * @param customerCode
     * @param password
     */
    public LoginDataItem(String customerCode, String password) {
        super();
        this.customerCode = customerCode;
        this.password = password;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
