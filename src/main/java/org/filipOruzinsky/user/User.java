package org.filipOruzinsky.user;

import java.util.Locale;
import java.util.Scanner;

public class User {
    public String role;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String state;
    private String phoneNumber;
    private String dogBreed;
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User() {
    }

    public User(String firstName, String lastName, String password, String address, String state, String phoneNumber, String dogBreed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.dogBreed = dogBreed;
    }


    public User(String firstName, String password, String role) {
        this.firstName = firstName;
        this.password = password;
        this.role = role;
    }

    public static Locale selectLanguage(Scanner scanner) {
        System.out.println("Choose your language: ");
        System.out.println("1. English");
        System.out.println("2. German");
        System.out.println("3. French");

        int languageChoice;
        if (scanner.hasNextInt()) {
            languageChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } else {
            System.out.println("Invalid input. Using default (English)...");
            scanner.nextLine(); // Consume invalid input
            return Locale.ENGLISH;
        }

        switch (languageChoice) {
            case 1:
                return Locale.ENGLISH;
            case 2:
                return Locale.GERMAN;
            case 3:
                return Locale.FRENCH;
            default:
                System.out.println("Invalid choice. Using default (English)...");
                return Locale.ENGLISH;
        }
    }

}
