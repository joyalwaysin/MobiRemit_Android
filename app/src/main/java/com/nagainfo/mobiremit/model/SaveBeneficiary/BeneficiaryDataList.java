package com.nagainfo.mobiremit.model.SaveBeneficiary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 03/08/17.
 */

public class BeneficiaryDataList {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private BeneficiaryDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public BeneficiaryDataList() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public BeneficiaryDataList(String sessionID, BeneficiaryDataItem data) {
        super();
        this.sessionID = sessionID;
        this.data = data;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public BeneficiaryDataItem getData() {
        return data;
    }

    public void setData(BeneficiaryDataItem data) {
        this.data = data;
    }
}
