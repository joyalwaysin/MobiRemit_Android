package com.nagainfo.mobiremit.model.Cancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 05/07/17.
 */

public class CancelDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("Refno")
    @Expose
    private String refno;

    /**
     * No args constructor for use in serialization
     *
     */
    public CancelDataItem() {
    }

    /**
     *
     * @param refno
     * @param customerCode
     */
    public CancelDataItem(String customerCode, String refno) {
        super();
        this.customerCode = customerCode;
        this.refno = refno;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }
}
