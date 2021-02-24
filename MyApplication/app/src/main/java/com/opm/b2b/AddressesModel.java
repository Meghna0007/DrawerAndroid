package com.opm.b2b;
public class AddressesModel {

    private String fullname;
    private String mobileNo;
    private String pincode;
    private String address;
    private Boolean selected;

    public AddressesModel(String fullname, String pincode, String address, Boolean selected,String mobileNo) {
        this.fullname = fullname;
        this.pincode = pincode;
        this.address = address;
        this.selected = selected;
        this.mobileNo=mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}