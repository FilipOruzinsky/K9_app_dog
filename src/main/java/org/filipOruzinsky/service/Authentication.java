package org.filipOruzinsky.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.interfaces.IAuthentication;
import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.filipOruzinsky.service.UserManagement.getUserByName;

public class Authentication extends User implements IAuthentication {

    private static final Logger logger = LogManager.getLogger(Authentication.class);
    String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json";


    private User loggedInUser;
//    @Override
    public boolean runWithUserInput(Locale currentLocale) throws IOException {
        logger.info("Entering runWithUserInput method");

        ResourceBundle formBundle = ResourceBundle.getBundle("messages",currentLocale);

        Scanner scanner = new Scanner(System.in);
        //TODO pridat resource bundle pre jazyky, upravit Interface pre methodu runWithUsersInput a performLogin


        String username = "";
        while (username.isEmpty()) {

            System.out.println(formBundle.getString("enter_username"));
            username = scanner.nextLine();

            if (username.isEmpty()) {
                System.out.println(formBundle.getString("username_required"));
            }

            logger.debug("User input - Username: {}", username);
        }

        String password = "";
        while (password.isEmpty()) {
            System.out.println(formBundle.getString("enter_password2"));
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println(formBundle.getString("password_required2"));
            }

            logger.debug("User input - Password: [hidden]");
        }

        loggedInUser = getUserByName(filePath, username);

        if (isValidCredentials(username, password,currentLocale)) {
            logger.info("Attempting login for user: {}", username);

            performLogin(username, currentLocale);
        } else {
            System.out.println(formBundle.getString("invalid_credentials"));

            logger.warn("Authentication failed for user: {}", username);
        }

        logger.info("Exiting runWithUserInput method");
        return false;
    }

//    @Override
    public void performLogin(String firstName, Locale currentLocale) {
        ResourceBundle formBundle = ResourceBundle.getBundle("messages",currentLocale);
        System.out.println(formBundle.getString("logging_user")+firstName);

        logger.info("Logging in : username {}", firstName);
        //avoid logging password because of security reason
        logger.debug("Login attempt: username {}", firstName);

    }
//    @Override
    public void logout(Locale currentLocale) {
        ResourceBundle formBundle = ResourceBundle.getBundle("messages",currentLocale);

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

        System.out.println(formBundle.getString("logout_message_for_user "));
    }


//    @Override
    public boolean isValidCredentials(String firstName, String password,Locale currentLocale) throws IOException {
        ResourceBundle formBundle = ResourceBundle.getBundle("messages",currentLocale);
        logger.info("Entering isValidCredentials method for user: {}", firstName);
        logger.debug("Method parameters - firstname: {}, password:{}", firstName, password);
        try {
            User user = getUserByName(filePath, firstName);
            if (user != null && user.getPassword().equals(password)) {
                logger.info("User {} successsfully authenticated", firstName);
                return true; // Username and password match
            } else
                System.out.println(formBundle.getString("invalid_password"));
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

