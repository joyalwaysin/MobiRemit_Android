package com.nagainfo.mobiremit.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 07/06/17.
 */

public class LoginData {
    @SerializedName("data")
    @Expose
    private LoginDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginData() {
    }

    /**
     *
     * @param data
     */
    public LoginData(LoginDataItem data) {
        super();
        this.data = data;
    }

    public LoginDataItem getData() {
        return data;
    }

    public void setData(LoginDataItem data) {
        this.data = data;
    }
}
