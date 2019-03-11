package com.nagainfo.mobiremit.model.Calculator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 12/07/17.
 */

public class CalculatorData {
    @SerializedName("data")
    @Expose
    private CalculatorDataItem data;

    /**
     * No args constructor for use in serialization
     *
     */
    public CalculatorData() {
    }

    /**
     *
     * @param data
     */
    public CalculatorData(CalculatorDataItem data) {
        super();
        this.data = data;
    }

    public CalculatorDataItem getData() {
        return data;
    }

    public void setData(CalculatorDataItem data) {
        this.data = data;
    }
}
