package com.nagainfo.mobiremit.model.Purpose;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 14/06/17.
 */

public class PurposeDataItem {
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
    public PurposeDataItem() {
    }

    /**
     *
     * @param customerCode
     * @param servNo
     */
    public PurposeDataItem(String customerCode, String servNo) {
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
