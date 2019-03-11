package com.nagainfo.mobiremit.model.ChangePin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 03/07/17.
 */

public class ChangePinDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("OldPin")
    @Expose
    private String oldPin;
    @SerializedName("NewPin")
    @Expose
    private String newPin;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePinDataItem() {
    }

    /**
     *
     * @param customerCode
     * @param oldPin
     * @param newPin
     */
    public ChangePinDataItem(String customerCode, String oldPin, String newPin) {
        super();
        this.customerCode = customerCode;
        this.oldPin = oldPin;
        this.newPin = newPin;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}
