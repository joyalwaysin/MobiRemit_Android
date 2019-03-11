package com.nagainfo.mobiremit.model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 06/06/17.
 */

public class RegisterData {
    @SerializedName("data")
    @Expose
    private RegisterDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterData() {
    }

    /**
     *
     * @param data
     */
    public RegisterData(RegisterDataItem data) {
        super();
        this.data = data;
    }

    public RegisterDataItem getData() {
        return data;
    }

    public void setData(RegisterDataItem data) {
        this.data = data;
    }
}
