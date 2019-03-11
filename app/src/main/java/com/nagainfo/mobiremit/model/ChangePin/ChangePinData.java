package com.nagainfo.mobiremit.model.ChangePin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 03/07/17.
 */

public class ChangePinData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private ChangePinDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePinData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public ChangePinData(String sessionID, ChangePinDataItem data) {
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

    public ChangePinDataItem getData() {
        return data;
    }

    public void setData(ChangePinDataItem data) {
        this.data = data;
    }
}
