package com.nagainfo.mobiremit.model.Beneficiary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 13/06/17.
 */

public class BeneficiaryItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("BenfName")
    @Expose
    private String benfName;
    @SerializedName("ServNO")
    @Expose
    private String servNO;
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
    private String service;
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
    @SerializedName("BranchCodes")
    @Expose
    private String branchCodes;

    /**
     * No args constructor for use in serialization
     *
     */
    public BeneficiaryItem() {
    }

    /**
     *
     * @param branchCodes
     * @param currencyCode
     * @param benfIDType
     * @param benfAddr2
     * @param benfAddr1
     * @param benfMobileNo
     * @param accountNo
     * @param benfBranch
     * @param benfIDNum
     * @param customerCode
     * @param service
     * @param servNO
     * @param benfName
     * @param benfCountry
     * @param benfBank
     */
    public BeneficiaryItem(String customerCode, String benfName, String servNO, String benfAddr1, String benfAddr2, String benfMobileNo, String benfCountry, String currencyCode, String service, String benfBank, String benfBranch, String accountNo, String benfIDType, String benfIDNum, String branchCodes) {
        super();
        this.customerCode = customerCode;
        this.benfName = benfName;
        this.servNO = servNO;
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
        this.branchCodes = branchCodes;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getBenfName() {
        return benfName;
    }

    public void setBenfName(String benfName) {
        this.benfName = benfName;
    }

    public String getServNO() {
        return servNO;
    }

    public void setServNO(String servNO) {
        this.servNO = servNO;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
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

    public String getBranchCodes() {
        return branchCodes;
    }

    public void setBranchCodes(String branchCodes) {
        this.branchCodes = branchCodes;
    }

}
