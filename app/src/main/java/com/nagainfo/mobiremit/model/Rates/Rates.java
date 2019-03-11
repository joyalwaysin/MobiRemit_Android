package com.nagainfo.mobiremit.model.Rates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 05/06/17.
 */

public class Rates {
    @SerializedName("CurCode")
    @Expose
    private String curCode;
    @SerializedName("BankRate")
    @Expose
    private Double bankRate;
    @SerializedName("CashSell")
    @Expose
    private Double cashSell;
    @SerializedName("CashBuy")
    @Expose
    private Double cashBuy;

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }

    public Double getBankRate() {
        return bankRate;
    }

    public void setBankRate(Double bankRate) {
        this.bankRate = bankRate;
    }

    public Double getCashSell() {
        return cashSell;
    }

    public void setCashSell(Double cashSell) {
        this.cashSell = cashSell;
    }

    public Double getCashBuy() {
        return cashBuy;
    }

    public void setCashBuy(Double cashBuy) {
        this.cashBuy = cashBuy;
    }
}
