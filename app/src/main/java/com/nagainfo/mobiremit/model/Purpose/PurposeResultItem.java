package com.nagainfo.mobiremit.model.Purpose;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 14/06/17.
 */

public class PurposeResultItem {
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
