package com.nagainfo.mobiremit.model.ChangeCredentials;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 08/06/17.
 */

public class PassPinDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("OldPassword")
    @Expose
    private String oldPassword;
    @SerializedName("NewPassword")
    @Expose
    private String newPassword;
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
    public PassPinDataItem() {
    }

    /**
     *
     * @param newPassword
     * @param customerCode
     * @param oldPin
     * @param oldPassword
     * @param newPin
     */
    public PassPinDataItem(String customerCode, String oldPassword, String newPassword, String oldPin, String newPin) {
        super();
        this.customerCode = customerCode;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.oldPin = oldPin;
        this.newPin = newPin;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
