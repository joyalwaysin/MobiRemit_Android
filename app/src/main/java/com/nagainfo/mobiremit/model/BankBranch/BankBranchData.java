package com.nagainfo.mobiremit.model.BankBranch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nagainfo.mobiremit.model.BeneficiaryBank.BenefBankDataItem;

/**
 * Created by joy on 28/07/17.
 */

public class BankBranchData {
    @SerializedName("data")
    @Expose
    private BankBranchDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BankBranchData() {
    }

    /**
     *
     * @param data
     */
    public BankBranchData(BankBranchDataItem data) {
        super();
        this.data = data;
    }

    public BankBranchDataItem getData() {
        return data;
    }

    public void setData(BankBranchDataItem data) {
        this.data = data;
    }
}
