package com.nagainfo.mobiremit.model.Currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 24/07/17.
 */

public class CurrencyDataItem {
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public CurrencyDataItem() {
    }

    /**
     *
     * @param countryCode
     */
    public CurrencyDataItem(String countryCode) {
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
