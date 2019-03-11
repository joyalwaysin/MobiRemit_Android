package com.nagainfo.mobiremit.model.IncomeSource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 14/06/17.
 */

public class IncomeSourceDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("ServNo")
    @Expose
    private String servNo;

    /**
     * No args constructor for use in serialization
     *
     */
    public IncomeSourceDataItem() {
    }

    /**
     *
     * @param customerCode
     * @param servNo
     */
    public IncomeSourceDataItem(String customerCode, String servNo) {
        super();
        this.customerCode = customerCode;
        this.servNo = servNo;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getServNo() {
        return servNo;
    }

    public void setServNo(String servNo) {
        this.servNo = servNo;
    }
}
