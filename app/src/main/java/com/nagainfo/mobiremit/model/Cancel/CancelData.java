package com.nagainfo.mobiremit.model.Cancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 05/07/17.
 */

public class CancelData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private CancelDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public CancelData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public CancelData(String sessionID, CancelDataItem data) {
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

    public CancelDataItem getData() {
        return data;
    }

    public void setData(CancelDataItem data) {
        this.data = data;
    }
}
