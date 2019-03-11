package com.nagainfo.mobiremit.model.RegisterNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 18/07/17.
 */

public class RegisterNewResult {
    @SerializedName("Response")
    @Expose
    private RegisterNewResultItem response;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("SessionID")
    @Expose
    private String sessionID;

    public RegisterNewResultItem getResponse() {
        return response;
    }

    public void setResponse(RegisterNewResultItem response) {
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
