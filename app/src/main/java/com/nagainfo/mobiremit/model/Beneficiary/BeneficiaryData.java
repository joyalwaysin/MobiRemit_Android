package com.nagainfo.mobiremit.model.Beneficiary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 13/06/17.
 */

public class BeneficiaryData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public BeneficiaryData() {
    }

    /**
     *
     * @param sessionID
     * @param customerCode
     */
    public BeneficiaryData(String sessionID, String customerCode) {
        super();
        this.sessionID = sessionID;
        this.customerCode = customerCode;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
