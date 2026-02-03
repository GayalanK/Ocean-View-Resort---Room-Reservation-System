package com.oceanview.resort.model;

import java.io.Serializable;

public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private String nicNumber;
    
    public Guest() {}
    
    public Guest(String name, String address, String contactNumber, String email, String nicNumber) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.nicNumber = nicNumber;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNicNumber() { return nicNumber; }
    public void setNicNumber(String nicNumber) { this.nicNumber = nicNumber; }
    
    @Override
    public String toString() {
        return "Guest{name='" + name + "', contact='" + contactNumber + "', email='" + email + "'}";
    }
}
