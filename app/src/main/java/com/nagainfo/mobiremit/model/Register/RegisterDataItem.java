package com.nagainfo.mobiremit.model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 06/06/17.
 */

public class RegisterDataItem {
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("IDType")
    @Expose
    private String iDType;
    @SerializedName("IDNo")
    @Expose
    private String iDNo;
    @SerializedName("DeviceType")
    @Expose
    private String deviceType;
    @SerializedName("DeviceID")
    @Expose
    private String deviceID;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterDataItem() {
    }

    /**
     *
     * @param deviceType
     * @param deviceID
     * @param customerCode
     * @param iDNo
     * @param iDType
     * @param mobileNo
     */
    public RegisterDataItem(String customerCode, String mobileNo, String iDType, String iDNo, String deviceType, String deviceID) {
        super();
        this.customerCode = customerCode;
        this.mobileNo = mobileNo;
        this.iDType = iDType;
        this.iDNo = iDNo;
        this.deviceType = deviceType;
        this.deviceID = deviceID;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIDType() {
        return iDType;
    }

    public void setIDType(String iDType) {
        this.iDType = iDType;
    }

    public String getIDNo() {
        return iDNo;
    }

    public void setIDNo(String iDNo) {
        this.iDNo = iDNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
