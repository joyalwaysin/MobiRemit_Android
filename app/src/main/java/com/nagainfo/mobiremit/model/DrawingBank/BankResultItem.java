package com.nagainfo.mobiremit.model.DrawingBank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joy on 16/06/17.
 */

public class BankResultItem {
    @SerializedName("PayBankCode")
    @Expose
    private String payBankCode;
    @SerializedName("PayBankName")
    @Expose
    private String payBankName;
    @SerializedName("PayAcntNumbers")
    @Expose
    private List<PayAcntNumber> payAcntNumbers = null;

    public String getPayBankCode() {
        return payBankCode;
    }

    public void setPayBankCode(String payBankCode) {
        this.payBankCode = payBankCode;
    }

    public String getPayBankName() {
        return payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public List<PayAcntNumber> getPayAcntNumbers() {
        return payAcntNumbers;
    }

    public void setPayAcntNumbers(List<PayAcntNumber> payAcntNumbers) {
        this.payAcntNumbers = payAcntNumbers;
    }

}
