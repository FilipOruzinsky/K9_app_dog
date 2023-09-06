package org.example.User;

public class User {
    public String getFirstName() {
        return firstName;
    }
    public User() {
        // You can initialize default values or leave it empty
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String phoneNumber;
    private String dogBreed;

    public User(String firstName, String lastName, String address, String state, String phoneNumber, String dogBreed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.dogBreed = dogBreed;
    }
}
