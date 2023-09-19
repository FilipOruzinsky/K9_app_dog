package org.example.user;


import org.example.MyLogger;
import org.example.interfaces.IUserLogin;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.user.RegisterUser.getUserByName;

public class UserLogin extends User implements IUserLogin {
    private User loggedInUser;
    private String firstName;

    String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json";

    private RegisterUser registerUser = new RegisterUser();

    public UserLogin() {
    }

    public User getLoggedInUser(String firstname) {
        return loggedInUser;
    }


//    public UserLogin(RegisterUser registerUser) {
//        this.registerUser = registerUser;
//    }

    @Override
    public void runWithUserInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please fill your credentials");
        System.out.print("Enter your username: ");
        firstName = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        loggedInUser = getUserByName(filePath,firstName);


        if (isValidCredentials(firstName, password)) {
            performLogin(firstName, password);

        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("You have been logged out.");
    }


    @Override
    public boolean isValidCredentials(String firstName, String password) throws IOException {

        User user = getUserByName(filePath, firstName);
        if (user != null && user.getPassword().equals(password)) {
            return true; // Username and password match
        }
        return false; // Invalid credentials
    }

    @Override
    public void performLogin(String username, String password) {
        System.out.println("Logging in user :" + username);
        MyLogger.info("Logging in : username " + username + " password " + password);

    }
    public String getPhoneNumber() {
        if (loggedInUser != null) {
            return loggedInUser.getPhoneNumber(); // Get the phone number from the logged-in user
        }
        return null; // Return null if no user is logged in
    }




    }


