package com.nagainfo.mobiremit.model.BeneficiaryBank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/07/17.
 */

public class BenefBankDataItem {
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public BenefBankDataItem() {
    }

    /**
     *
     * @param countryCode
     */
    public BenefBankDataItem(String countryCode) {
        super();
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
