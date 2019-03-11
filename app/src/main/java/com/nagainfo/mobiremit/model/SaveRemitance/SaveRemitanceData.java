package com.nagainfo.mobiremit.model.SaveRemitance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/06/17.
 */

public class SaveRemitanceData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private SaveRemitanceDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public SaveRemitanceData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public SaveRemitanceData(String sessionID, SaveRemitanceDataItem data) {
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

    public SaveRemitanceDataItem getData() {
        return data;
    }

    public void setData(SaveRemitanceDataItem data) {
        this.data = data;
    }

}
