package com.nagainfo.mobiremit.model.BankBranch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joy on 28/07/17.
 */

public class BankBranchDataItem {
    @SerializedName("OtherDtlCode")
    @Expose
    private String otherDtlCode;
    @SerializedName("OtherDtlValue")
    @Expose
    private String otherDtlValue;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public BankBranchDataItem() {
    }

    /**
     *
     * @param countryCode
     * @param otherDtlCode
     * @param otherDtlValue
     */
    public BankBranchDataItem(String otherDtlCode, String otherDtlValue, String countryCode) {
        super();
        this.otherDtlCode = otherDtlCode;
        this.otherDtlValue = otherDtlValue;
        this.countryCode = countryCode;
    }

    public String getOtherDtlCode() {
        return otherDtlCode;
    }

    public void setOtherDtlCode(String otherDtlCode) {
        this.otherDtlCode = otherDtlCode;
    }

    public String getOtherDtlValue() {
        return otherDtlValue;
    }

    public void setOtherDtlValue(String otherDtlValue) {
        this.otherDtlValue = otherDtlValue;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
