package com.rajdeep.food4all;

public class NGO {
    private String name;
    private String location;
    private String address;

    public NGO(String name, String location, String address) {
        this.name = name;
        this.location = location;
        this.address = address;
    }

    // Getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getAddress() { return address; }
}