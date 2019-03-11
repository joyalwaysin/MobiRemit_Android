package com.nagainfo.mobiremit.model.RegisterNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 18/07/17.
 */

public class RegisterNewData {
    @SerializedName("data")
    @Expose
    private RegisterNewDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterNewData() {
    }

    /**
     *
     * @param data
     */
    public RegisterNewData(RegisterNewDataItem data) {
        super();
        this.data = data;
    }

    public RegisterNewDataItem getData() {
        return data;
    }

    public void setData(RegisterNewDataItem data) {
        this.data = data;
    }
}
