package org.example.user;

public class User {
    public String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
    private String password;
    private String address;
    private String state;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    private String phoneNumber;
    private String dogBreed;

    public User(String firstName, String lastName,String password, String address, String state, String phoneNumber, String dogBreed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.dogBreed = dogBreed;
    }
    public User(String firstName,String password,String role){
        this.firstName = firstName;
        this.password = password;
        this.role = role;
    }

}
