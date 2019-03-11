package com.nagainfo.mobiremit.model.RegisterNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 18/07/17.
 */

public class RegisterNewResultItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

}
