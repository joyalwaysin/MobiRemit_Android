package com.nagainfo.mobiremit.model.CodeType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joy on 25/07/17.
 */

public class CodeTypeList {
    @SerializedName("Response")
    @Expose
    private List<CodeTypeItem> response = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("SessionID")
    @Expose
    private String sessionID;

    public List<CodeTypeItem> getResponse() {
        return response;
    }

    public void setResponse(List<CodeTypeItem> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

}
