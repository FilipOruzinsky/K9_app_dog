package org.filipOruzinsky.user;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Scanner;

@Entity(name = "User")
@Table(name = "USERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Component
public class User implements Serializable {
    //    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @Id
    @SequenceGenerator(name = "USER_ID_SEQ", allocationSize = 1, sequenceName = "USER_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")

    @Column(name = "USER_ID", nullable = false)
    private Long userID;

    //    public String role;
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "STATE", length = 50)
    private String state;
    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;
    @Column(name = "DOG_BREED", length = 50)
    private String dogBreed;

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

    public User(String firstName, String password) {
        this.firstName = firstName;
        this.password = password;
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dogBreed='" + dogBreed + '\'' +
                '}';
    }
}
