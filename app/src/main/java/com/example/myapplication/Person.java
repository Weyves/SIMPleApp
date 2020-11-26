package com.example.myapplication;

public class Person {
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int phoneNumber;

    @Override
    public String toString() { return "Name: " + name + "\nPhone: " + phoneNumber; }
}
