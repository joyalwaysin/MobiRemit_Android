package com.nagainfo.mobiremit.model.Branches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 19/07/17.
 */

public class BranchDataItem {
    @SerializedName("BankCode")
    @Expose
    private String bankCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public BranchDataItem() {
    }

    /**
     *
     * @param bankCode
     */
    public BranchDataItem(String bankCode) {
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
