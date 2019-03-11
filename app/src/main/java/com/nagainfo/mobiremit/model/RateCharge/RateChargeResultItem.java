package com.nagainfo.mobiremit.model.RateCharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 15/06/17.
 */

public class RateChargeResultItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("ServNo")
    @Expose
    private String servNo;
    @SerializedName("FcyAmount")
    @Expose
    private Double fcyAmount;
    @SerializedName("LcyAmount")
    @Expose
    private Double lcyAmount;
    @SerializedName("MRate")
    @Expose
    private Double mRate;
    @SerializedName("DRate")
    @Expose
    private Double dRate;
    @SerializedName("Charge")
    @Expose
    private Double charge;
    @SerializedName("LocVsSndRate")
    @Expose
    private Double locVsSndRate;
    @SerializedName("LocVsSndAmt")
    @Expose
    private Double locVsSndAmt;
    @SerializedName("RcvCurrency")
    @Expose
    private String rcvCurrency;

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

    public Double getFcyAmount() {
        return fcyAmount;
    }

    public void setFcyAmount(Double fcyAmount) {
        this.fcyAmount = fcyAmount;
    }

    public Double getLcyAmount() {
        return lcyAmount;
    }

    public void setLcyAmount(Double lcyAmount) {
        this.lcyAmount = lcyAmount;
    }

    public Double getMRate() {
        return mRate;
    }

    public void setMRate(Double mRate) {
        this.mRate = mRate;
    }

    public Double getDRate() {
        return dRate;
    }

    public void setDRate(Double dRate) {
        this.dRate = dRate;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getLocVsSndRate() {
        return locVsSndRate;
    }

    public void setLocVsSndRate(Double locVsSndRate) {
        this.locVsSndRate = locVsSndRate;
    }

    public Double getLocVsSndAmt() {
        return locVsSndAmt;
    }

    public void setLocVsSndAmt(Double locVsSndAmt) {
        this.locVsSndAmt = locVsSndAmt;
    }

    public String getRcvCurrency() {
        return rcvCurrency;
    }

    public void setRcvCurrency(String rcvCurrency) {
        this.rcvCurrency = rcvCurrency;
    }
}
