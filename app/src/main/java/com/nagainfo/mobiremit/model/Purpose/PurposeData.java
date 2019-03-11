package com.nagainfo.mobiremit.model.Purpose;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 14/06/17.
 */

public class PurposeData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private PurposeDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public PurposeData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public PurposeData(String sessionID, PurposeDataItem data) {
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

    public PurposeDataItem getData() {
        return data;
    }

    public void setData(PurposeDataItem data) {
        this.data = data;
    }
}
