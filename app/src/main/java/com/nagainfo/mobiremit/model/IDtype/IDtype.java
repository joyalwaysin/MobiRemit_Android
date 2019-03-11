package com.nagainfo.mobiremit.model.IDtype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 01/06/17.
 */

public class IDtype {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Description")
    @Expose
    private String description;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}