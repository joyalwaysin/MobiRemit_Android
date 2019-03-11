package com.nagainfo.mobiremit.model.ChangePass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 04/07/17.
 */

public class ChangePassDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("OldPassword")
    @Expose
    private String oldPassword;
    @SerializedName("NewPassword")
    @Expose
    private String newPassword;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePassDataItem() {
    }

    /**
     *
     * @param newPassword
     * @param customerCode
     * @param oldPassword
     */
    public ChangePassDataItem(String customerCode, String oldPassword, String newPassword) {
        super();
        this.customerCode = customerCode;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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

}
