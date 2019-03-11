package com.nagainfo.mobiremit.model.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/06/17.
 */

public class HistoryData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private HistoryDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public HistoryData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public HistoryData(String sessionID, HistoryDataItem data) {
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

    public HistoryDataItem getData() {
        return data;
    }

    public void setData(HistoryDataItem data) {
        this.data = data;
    }
}
