package com.nagainfo.mobiremit.model.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/06/17.
 */

public class HistoryResultItem {
    @SerializedName("Refno")
    @Expose
    private String refno;
    @SerializedName("Trandate")
    @Expose
    private String trandate;
    @SerializedName("BenfName")
    @Expose
    private String benfName;
    @SerializedName("BenfCountry")
    @Expose
    private String benfCountry;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("AccountNo")
    @Expose
    private String accountNo;
    @SerializedName("BankBranch")
    @Expose
    private String bankBranch;
    @SerializedName("FCYAmt")
    @Expose
    private Double fCYAmt;
    @SerializedName("LCYAmt")
    @Expose
    private Double lCYAmt;
    @SerializedName("Charges")
    @Expose
    private Double charges;
    @SerializedName("MRate")
    @Expose
    private Double mRate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("BenfMobileNo")
    @Expose
    private String benfMobileNo;

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getTrandate() {
        return trandate;
    }

    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }

    public String getBenfName() {
        return benfName;
    }

    public void setBenfName(String benfName) {
        this.benfName = benfName;
    }

    public String getBenfCountry() {
        return benfCountry;
    }

    public void setBenfCountry(String benfCountry) {
        this.benfCountry = benfCountry;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Double getFCYAmt() {
        return fCYAmt;
    }

    public void setFCYAmt(Double fCYAmt) {
        this.fCYAmt = fCYAmt;
    }

    public Double getLCYAmt() {
        return lCYAmt;
    }

    public void setLCYAmt(Double lCYAmt) {
        this.lCYAmt = lCYAmt;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public Double getMRate() {
        return mRate;
    }

    public void setMRate(Double mRate) {
        this.mRate = mRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBenfMobileNo() {
        return benfMobileNo;
    }

    public void setBenfMobileNo(String benfMobileNo) {
        this.benfMobileNo = benfMobileNo;
    }
}
