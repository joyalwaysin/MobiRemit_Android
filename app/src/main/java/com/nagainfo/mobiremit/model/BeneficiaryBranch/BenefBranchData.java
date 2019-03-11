package com.nagainfo.mobiremit.model.BeneficiaryBranch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/07/17.
 */

public class BenefBranchData {
    @SerializedName("data")
    @Expose
    private BenefBranchDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BenefBranchData() {
    }

    /**
     *
     * @param data
     */
    public BenefBranchData(BenefBranchDataItem data) {
        super();
        this.data = data;
    }

    public BenefBranchDataItem getData() {
        return data;
    }

    public void setData(BenefBranchDataItem data) {
        this.data = data;
    }
}
