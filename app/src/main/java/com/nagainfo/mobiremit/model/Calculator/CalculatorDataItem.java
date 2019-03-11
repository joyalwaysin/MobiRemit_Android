package com.nagainfo.mobiremit.model.Calculator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 12/07/17.
 */

public class CalculatorDataItem {
    @SerializedName("FromCurrency")
    @Expose
    private String fromCurrency;
    @SerializedName("ToCurrency")
    @Expose
    private String toCurrency;
    @SerializedName("FromAmount")
    @Expose
    private String fromAmount;

    /**
     * No args constructor for use in serialization
     *
     */
    public CalculatorDataItem() {
    }

    /**
     *
     * @param fromCurrency
     * @param fromAmount
     * @param toCurrency
     */
    public CalculatorDataItem(String fromCurrency, String toCurrency, String fromAmount) {
        super();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(String fromAmount) {
        this.fromAmount = fromAmount;
    }
}
