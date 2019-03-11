package com.nagainfo.mobiremit.model.SaveRemitance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 28/06/17.
 */

public class SaveRemitanceResultItem {
    @SerializedName("Refno")
    @Expose
    private String refno;
    @SerializedName("ClientRefno")
    @Expose
    private String clientRefno;
    @SerializedName("TokenNo")
    @Expose
    private String tokenNo;

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getClientRefno() {
        return clientRefno;
    }

    public void setClientRefno(String clientRefno) {
        this.clientRefno = clientRefno;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }
}
