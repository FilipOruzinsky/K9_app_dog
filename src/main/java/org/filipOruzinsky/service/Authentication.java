package org.filipOruzinsky.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.interfaces.IAuthentication;
import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import static org.filipOruzinsky.service.UserManagement.getUserByName;

public class Authentication extends User implements IAuthentication {

    private static final Logger logger = LogManager.getLogger(Authentication.class);
    String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json";


    private User loggedInUser;
    @Override
    public void runWithUserInput() throws IOException {
        logger.info("Entering runWithUserInput method");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please fill in your credentials");

        String username = "";
        while (username.isEmpty()) {
            System.out.print("Enter your username: ");
            username = scanner.nextLine();

            if (username.isEmpty()) {
                System.out.println("Username is required.");
            }

            logger.debug("User input - Username: {}", username);
        }

        String password = "";
        while (password.isEmpty()) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("Password is required.");
            }

            logger.debug("User input - Password: [hidden]");
        }

        loggedInUser = getUserByName(filePath, username);

        if (isValidCredentials(username, password)) {
            logger.info("Attempting login for user: {}", username);

            performLogin(username, password);
        } else {
            System.out.println("Invalid credentials. Login failed.");

            logger.warn("Authentication failed for user: {}", username);
        }

        logger.info("Exiting runWithUserInput method");
    }

    @Override
    public void performLogin(String firstName, String password) {
        System.out.println("Logging in user :" + firstName);
        logger.info("Logging in : username {}", firstName);
        //avoid logging password because of security reason
        logger.debug("Login attempt: username {}", firstName);

    }
    @Override
    public void logout() {
        logger.info("Entering logout method");
        logger.debug("Method parameters - NONE");
        if (loggedInUser != null){
            logger.info("Logging out user : {}",loggedInUser.getFirstName());
            loggedInUser = null;
            logger.info("User logged out successfully");
        }else {
            logger.warn("No user is logged in. Logout method called without an active session.");
        }
        logger.info("Exiting logout method");

        System.out.println("You have been logged out.");
    }


    @Override
    public boolean isValidCredentials(String firstName, String password) throws IOException {
        logger.info("Entering isValidCredentials method for user: {}", firstName);
        logger.debug("Method parameters - firstname: {}, password:{}", firstName, password);
        try {
            User user = getUserByName(filePath, firstName);
            if (user != null && user.getPassword().equals(password)) {
                logger.info("User {} successsfully authenticated", firstName);
                return true; // Username and password match
            } else
                logger.warn("Authentication failed for user: {}", firstName);
            return false; // Invalid credentials
        } catch (IOException e) {
            logger.error("Error while checking credentials for user: {}", firstName, e);
            throw e;
        } finally {
            logger.info("Exiting isValidCredentials method for user: {}", firstName);

        }
    }
    @Override
    public String getPhoneNumber() {
        logger.info("Entering getPhoneNumber method");
        if (loggedInUser != null) {

            logger.debug("Methods parameter - NONE");
            String phoneNumber = loggedInUser.getPhoneNumber();
            logger.info("Retrieved phone number '{}' for user : {}", phoneNumber, loggedInUser.getFirstName());
            logger.info("Exiting getPhoneNumber method (Success");
            return phoneNumber;

        } else {
            logger.warn("No useris logged in. Returning null for getPhoneNumber.");
            logger.info("Exiting getPhoneNumber method (No user logged in)");
            return  null; // returning null if no user logged in
        }
    }
}
