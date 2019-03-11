package com.nagainfo.mobiremit.model.RegisterNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 18/07/17.
 */

public class RegisterNewDataItem {
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Addr1")
    @Expose
    private String addr1;
    @SerializedName("Addr2")
    @Expose
    private String addr2;
    @SerializedName("POBox")
    @Expose
    private String pOBox;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Nationality")
    @Expose
    private String nationality;
    @SerializedName("IDType")
    @Expose
    private String iDType;
    @SerializedName("IDNo")
    @Expose
    private String iDNo;
    @SerializedName("IDIssueDate")
    @Expose
    private String iDIssueDate;
    @SerializedName("IDExpiryDate")
    @Expose
    private String iDExpiryDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterNewDataItem() {
    }

    /**
     *
     * @param lastName
     * @param nationality
     * @param pOBox
     * @param addr1
     * @param email
     * @param addr2
     * @param iDExpiryDate
     * @param iDNo
     * @param firstName
     * @param iDType
     * @param mobileNo
     * @param iDIssueDate
     */
    public RegisterNewDataItem(String firstName, String lastName, String addr1, String addr2, String pOBox, String mobileNo, String email, String nationality, String iDType, String iDNo, String iDIssueDate, String iDExpiryDate) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.pOBox = pOBox;
        this.mobileNo = mobileNo;
        this.email = email;
        this.nationality = nationality;
        this.iDType = iDType;
        this.iDNo = iDNo;
        this.iDIssueDate = iDIssueDate;
        this.iDExpiryDate = iDExpiryDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getPOBox() {
        return pOBox;
    }

    public void setPOBox(String pOBox) {
        this.pOBox = pOBox;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getIDIssueDate() {
        return iDIssueDate;
    }

    public void setIDIssueDate(String iDIssueDate) {
        this.iDIssueDate = iDIssueDate;
    }

    public String getIDExpiryDate() {
        return iDExpiryDate;
    }

    public void setIDExpiryDate(String iDExpiryDate) {
        this.iDExpiryDate = iDExpiryDate;
    }
}
