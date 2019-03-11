package com.nagainfo.mobiremit.model.BeneficiaryBank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/07/17.
 */

public class BenefBankData {
    @SerializedName("data")
    @Expose
    private BenefBankDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BenefBankData() {
    }

    /**
     *
     * @param data
     */
    public BenefBankData(BenefBankDataItem data) {
        super();
        this.data = data;
    }

    public BenefBankDataItem getData() {
        return data;
    }

    public void setData(BenefBankDataItem data) {
        this.data = data;
    }

}
