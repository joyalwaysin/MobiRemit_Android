package com.nagainfo.mobiremit.model.Branches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 07/06/17.
 */

public class BranchData {
    @SerializedName("data")
    @Expose
    private BranchDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BranchData() {
    }

    /**
     *
     * @param data
     */
    public BranchData(BranchDataItem data) {
        super();
        this.data = data;
    }

    public BranchDataItem getData() {
        return data;
    }

    public void setData(BranchDataItem data) {
        this.data = data;
    }

}
