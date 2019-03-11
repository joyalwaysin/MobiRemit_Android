package com.nagainfo.mobiremit.model.Country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nagainfo.mobiremit.model.IDtype.IDtype;

import java.util.List;

/**
 * Created by joy on 01/06/17.
 */

public class CountryList {

    @SerializedName("Response")
    @Expose
    private List<CountryItem> Response = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("SessionID")
    @Expose
    private Object sessionID;

    public List<CountryItem> getResponse() {
        return Response;
    }

    public void setResponse(List<CountryItem> response) {
        this.Response = response;
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

    public Object getSessionID() {
        return sessionID;
    }

    public void setSessionID(Object sessionID) {
        this.sessionID = sessionID;
    }

}
