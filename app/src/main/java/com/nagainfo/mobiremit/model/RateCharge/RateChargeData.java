package com.nagainfo.mobiremit.model.RateCharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 15/06/17.
 */

public class RateChargeData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private RateChargeDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public RateChargeData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public RateChargeData(String sessionID, RateChargeDataItem data) {
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

    public RateChargeDataItem getData() {
        return data;
    }

    public void setData(RateChargeDataItem data) {
        this.data = data;
    }
}
