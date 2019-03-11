package com.nagainfo.mobiremit.model.BeneficiaryBank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joy on 28/07/17.
 */

public class BenefBankResultItem {
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
