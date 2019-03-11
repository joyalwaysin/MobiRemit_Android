package com.nagainfo.mobiremit.model.BeneficiaryBranch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/07/17.
 */

public class BenefBranchDataItem {
    @SerializedName("BankCode")
    @Expose
    private String bankCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public BenefBranchDataItem() {
    }

    /**
     *
     * @param bankCode
     */
    public BenefBranchDataItem(String bankCode) {
        super();
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
