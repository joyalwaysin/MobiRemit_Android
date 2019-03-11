package com.nagainfo.mobiremit.model.Calculator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 12/07/17.
 */

public class CalculatorResultItem {
    @SerializedName("FromCurrency")
    @Expose
    private String fromCurrency;
    @SerializedName("ToCurrency")
    @Expose
    private String toCurrency;
    @SerializedName("FromAmount")
    @Expose
    private Double fromAmount;
    @SerializedName("ToAmount")
    @Expose
    private Double toAmount;

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

    public Double getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Double fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Double getToAmount() {
        return toAmount;
    }

    public void setToAmount(Double toAmount) {
        this.toAmount = toAmount;
    }
}
