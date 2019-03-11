package com.nagainfo.mobiremit.model.SaveRemitance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/06/17.
 */

public class SaveRemitanceDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("ServNo")
    @Expose
    private String servNo;
    @SerializedName("MRate")
    @Expose
    private String mRate;
    @SerializedName("FCYAmt")
    @Expose
    private String fCYAmt;
    @SerializedName("LCYAmt")
    @Expose
    private String lCYAmt;
    @SerializedName("Charges")
    @Expose
    private String charges;
    @SerializedName("PurposeCode")
    @Expose
    private String purposeCode;
    @SerializedName("SourceCode")
    @Expose
    private String sourceCode;
    @SerializedName("LocVsSndAmt")
    @Expose
    private String locVsSndAmt;
    @SerializedName("PayMode")
    @Expose
    private String payMode;
    @SerializedName("PayBankCode")
    @Expose
    private String payBankCode;
    @SerializedName("PayAcntNumber")
    @Expose
    private String payAcntNumber;
    @SerializedName("PayentRefno")
    @Expose
    private String payentRefno;
    @SerializedName("PayDate")
    @Expose
    private String payDate;
    @SerializedName("Pin")
    @Expose
    private String pin;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveRemitanceDataItem() {
    }

    /**
     *
     * @param payentRefno
     * @param payMode
     * @param charges
     * @param locVsSndAmt
     * @param sourceCode
     * @param fCYAmt
     * @param payAcntNumber
     * @param lCYAmt
     * @param customerCode
     * @param payBankCode
     * @param payDate
     * @param servNo
     * @param mRate
     * @param purposeCode
     */
    public SaveRemitanceDataItem(String customerCode, String servNo, String mRate, String fCYAmt, String lCYAmt,
                                 String charges, String purposeCode, String sourceCode, String locVsSndAmt,
                                 String payMode, String payBankCode, String payAcntNumber, String payentRefno,
                                 String payDate, String pin) {
        super();
        this.customerCode = customerCode;
        this.servNo = servNo;
        this.mRate = mRate;
        this.fCYAmt = fCYAmt;
        this.lCYAmt = lCYAmt;
        this.charges = charges;
        this.purposeCode = purposeCode;
        this.sourceCode = sourceCode;
        this.locVsSndAmt = locVsSndAmt;
        this.payMode = payMode;
        this.payBankCode = payBankCode;
        this.payAcntNumber = payAcntNumber;
        this.payentRefno = payentRefno;
        this.payDate = payDate;
        this.pin = pin;
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

    public String getMRate() {
        return mRate;
    }

    public void setMRate(String mRate) {
        this.mRate = mRate;
    }

    public String getFCYAmt() {
        return fCYAmt;
    }

    public void setFCYAmt(String fCYAmt) {
        this.fCYAmt = fCYAmt;
    }

    public String getLCYAmt() {
        return lCYAmt;
    }

    public void setLCYAmt(String lCYAmt) {
        this.lCYAmt = lCYAmt;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getLocVsSndAmt() {
        return locVsSndAmt;
    }

    public void setLocVsSndAmt(String locVsSndAmt) {
        this.locVsSndAmt = locVsSndAmt;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayBankCode() {
        return payBankCode;
    }

    public void setPayBankCode(String payBankCode) {
        this.payBankCode = payBankCode;
    }

    public String getPayAcntNumber() {
        return payAcntNumber;
    }

    public void setPayAcntNumber(String payAcntNumber) {
        this.payAcntNumber = payAcntNumber;
    }

    public String getPayentRefno() {
        return payentRefno;
    }

    public void setPayentRefno(String payentRefno) {
        this.payentRefno = payentRefno;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
