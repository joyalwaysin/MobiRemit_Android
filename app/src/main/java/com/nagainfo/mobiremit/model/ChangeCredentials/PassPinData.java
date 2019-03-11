package com.nagainfo.mobiremit.model.ChangeCredentials;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 08/06/17.
 */

public class PassPinData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private PassPinDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public PassPinData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public PassPinData(String sessionID, PassPinDataItem data) {
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

    public PassPinDataItem getData() {
        return data;
    }

    public void setData(PassPinDataItem data) {
        this.data = data;
    }
}

