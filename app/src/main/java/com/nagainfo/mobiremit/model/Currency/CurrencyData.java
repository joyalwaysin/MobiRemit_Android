package com.nagainfo.mobiremit.model.Currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 24/07/17.
 */

public class CurrencyData {
    @SerializedName("data")
    @Expose
    private CurrencyDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public CurrencyData() {
    }

    /**
     *
     * @param data
     */
    public CurrencyData(CurrencyDataItem data) {
        super();
        this.data = data;
    }

    public CurrencyDataItem getData() {
        return data;
    }

    public void setData(CurrencyDataItem data) {
        this.data = data;
    }
}
