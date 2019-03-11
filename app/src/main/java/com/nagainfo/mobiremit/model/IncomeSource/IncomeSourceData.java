package com.nagainfo.mobiremit.model.IncomeSource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nagainfo.mobiremit.model.Purpose.PurposeDataItem;

/**
 * Created by joy on 14/06/17.
 */

public class IncomeSourceData {
    @SerializedName("SessionID")
    @Expose
    private String sessionID;
    @SerializedName("data")
    @Expose
    private IncomeSourceDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public IncomeSourceData() {
    }

    /**
     *
     * @param sessionID
     * @param data
     */
    public IncomeSourceData(String sessionID, IncomeSourceDataItem data) {
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

    public IncomeSourceDataItem getData() {
        return data;
    }

    public void setData(IncomeSourceDataItem data) {
        this.data = data;
    }
}
