package com.nagainfo.mobiremit.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joy on 07/06/17.
 */

public class LoginResultList {
    @SerializedName("Response")
    @Expose
    private List<LoginResultItem> response = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("SessionID")
    @Expose
    private String sessionID;

    public List<LoginResultItem> getResponse() {
        return response;
    }

    public void setResponse(List<LoginResultItem> response) {
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
