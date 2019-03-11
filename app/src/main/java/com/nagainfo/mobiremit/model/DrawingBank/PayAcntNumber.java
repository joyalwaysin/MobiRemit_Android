package com.nagainfo.mobiremit.model.DrawingBank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 03/07/17.
 */

public class PayAcntNumber {
    @SerializedName("PayAcntNumber")
    @Expose
    private String payAcntNumber;

    public String getPayAcntNumber() {
        return payAcntNumber;
    }

    public void setPayAcntNumber(String payAcntNumber) {
        this.payAcntNumber = payAcntNumber;
    }
}
