package com.nagainfo.mobiremit.model.RateCharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 15/06/17.
 */

public class RateChargeDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("ServNo")
    @Expose
    private String servNo;
    @SerializedName("AmountType")
    @Expose
    private String amountType;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("RcvCurrency")
    @Expose
    private String rcvCurrency;

    /**
     * No args constructor for use in serialization
     *
     */
    public RateChargeDataItem() {
    }

    /**
     *
     * @param amount
     * @param customerCode
     * @param rcvCurrency
     * @param servNo
     * @param amountType
     */
    public RateChargeDataItem(String customerCode, String servNo, String amountType, String amount, String rcvCurrency) {
        super();
        this.customerCode = customerCode;
        this.servNo = servNo;
        this.amountType = amountType;
        this.amount = amount;
        this.rcvCurrency = rcvCurrency;
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

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRcvCurrency() {
        return rcvCurrency;
    }

    public void setRcvCurrency(String rcvCurrency) {
        this.rcvCurrency = rcvCurrency;
    }
}
