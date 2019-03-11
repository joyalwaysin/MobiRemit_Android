package com.nagainfo.mobiremit.model.ChangePass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 04/07/17.
 */

public class ChangePassData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private ChangePassDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePassData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public ChangePassData(String sessionID, ChangePassDataItem data) {
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

    public ChangePassDataItem getData() {
        return data;
    }

    public void setData(ChangePassDataItem data) {
        this.data = data;
    }
}
