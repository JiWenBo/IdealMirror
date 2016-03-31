package com.example.dllo.idealmirror.bean;

/**
 * Created by LYH on 16/3/31.
 */
public class Address {
    private String addressee;//收件人
    private String address;//地址
    private String phoneNumber;//电话号码

    public Address(String addressee, String address, String phoneNumber) {
        this.addressee = addressee;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
