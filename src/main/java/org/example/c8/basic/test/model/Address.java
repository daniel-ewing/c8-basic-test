package org.example.c8.basic.test.model;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zipCode;

    public Address(String firstName, String lastName, String line1, String line2, String city, String state, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    public Address(String firstName, String lastName, String line1, String city, String state, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.line1 = line1;
        this.line2 = null;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address(String line1, String line2, String city, String state, String zipCode) {
        this.firstName = null;
        this.lastName = null;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address(String line1, String city, String state, String zipCode) {
        this.firstName = null;
        this.lastName = null;
        this.line1 = line1;
        this.line2 = null;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
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

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
