package com.nagainfo.mobiremit.model.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/06/17.
 */

public class HistoryDataItem {
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
    public HistoryDataItem() {
    }

    /**
     *
     * @param refno
     * @param customerCode
     */
    public HistoryDataItem(String customerCode, String refno) {
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
