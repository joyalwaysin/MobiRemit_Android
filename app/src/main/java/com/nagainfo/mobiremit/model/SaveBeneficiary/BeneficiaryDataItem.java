package com.nagainfo.mobiremit.model.SaveBeneficiary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 03/08/17.
 */

public class BeneficiaryDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("BenFirstName")
    @Expose
    private String benFirstName;
    @SerializedName("BenLastName")
    @Expose
    private String benLastName;
    @SerializedName("BenfAddr1")
    @Expose
    private String benfAddr1;
    @SerializedName("BenfAddr2")
    @Expose
    private String benfAddr2;
    @SerializedName("BenfMobileNo")
    @Expose
    private String benfMobileNo;
    @SerializedName("BenfCountry")
    @Expose
    private String benfCountry;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("Service")
    @Expose
    private Character service;
    @SerializedName("BenfBank")
    @Expose
    private String benfBank;
    @SerializedName("BenfBranch")
    @Expose
    private String benfBranch;
    @SerializedName("AccountNo")
    @Expose
    private String accountNo;
    @SerializedName("BenfIDType")
    @Expose
    private String benfIDType;
    @SerializedName("BenfIDNum")
    @Expose
    private String benfIDNum;

    /**
     * No args constructor for use in serialization
     *
     */
    public BeneficiaryDataItem() {
    }

    /**
     *
     * @param currencyCode
     * @param benfIDType
     * @param benLastName
     * @param benfAddr2
     * @param benfAddr1
     * @param benfMobileNo
     * @param accountNo
     * @param benfBranch
     * @param benfIDNum
     * @param benFirstName
     * @param customerCode
     * @param service
     * @param benfCountry
     * @param benfBank
     */
    public BeneficiaryDataItem(String customerCode, String benFirstName, String benLastName, String benfAddr1, String benfAddr2, String benfMobileNo, String benfCountry, String currencyCode, Character service, String benfBank, String benfBranch, String accountNo, String benfIDType, String benfIDNum) {
        super();
        this.customerCode = customerCode;
        this.benFirstName = benFirstName;
        this.benLastName = benLastName;
        this.benfAddr1 = benfAddr1;
        this.benfAddr2 = benfAddr2;
        this.benfMobileNo = benfMobileNo;
        this.benfCountry = benfCountry;
        this.currencyCode = currencyCode;
        this.service = service;
        this.benfBank = benfBank;
        this.benfBranch = benfBranch;
        this.accountNo = accountNo;
        this.benfIDType = benfIDType;
        this.benfIDNum = benfIDNum;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getBenFirstName() {
        return benFirstName;
    }

    public void setBenFirstName(String benFirstName) {
        this.benFirstName = benFirstName;
    }

    public String getBenLastName() {
        return benLastName;
    }

    public void setBenLastName(String benLastName) {
        this.benLastName = benLastName;
    }

    public String getBenfAddr1() {
        return benfAddr1;
    }

    public void setBenfAddr1(String benfAddr1) {
        this.benfAddr1 = benfAddr1;
    }

    public String getBenfAddr2() {
        return benfAddr2;
    }

    public void setBenfAddr2(String benfAddr2) {
        this.benfAddr2 = benfAddr2;
    }

    public String getBenfMobileNo() {
        return benfMobileNo;
    }

    public void setBenfMobileNo(String benfMobileNo) {
        this.benfMobileNo = benfMobileNo;
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

    public Character getService() {
        return service;
    }

    public void setService(Character service) {
        this.service = service;
    }

    public String getBenfBank() {
        return benfBank;
    }

    public void setBenfBank(String benfBank) {
        this.benfBank = benfBank;
    }

    public String getBenfBranch() {
        return benfBranch;
    }

    public void setBenfBranch(String benfBranch) {
        this.benfBranch = benfBranch;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBenfIDType() {
        return benfIDType;
    }

    public void setBenfIDType(String benfIDType) {
        this.benfIDType = benfIDType;
    }

    public String getBenfIDNum() {
        return benfIDNum;
    }

    public void setBenfIDNum(String benfIDNum) {
        this.benfIDNum = benfIDNum;
    }
}
