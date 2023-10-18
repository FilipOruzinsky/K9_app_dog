package org.filipOruzinsky.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Security {

    private static final Logger logger = LogManager.getLogger(Security.class);

    public boolean isPhoneNumberAlreadyRegistered(String phoneNumber) throws IOException {
        logger.info("Entering isPhoneNumberAlreadyRegistered method for phone number: {}", phoneNumber);

        List<User> existingUsers = new ArrayList<>();

        try {
            UserManagement userManagement = new UserManagement();
            existingUsers = userManagement.readUsersFromJsonFile("/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json");
            logger.debug("Users successfully read from JSON file for phone number check.");
        } catch (IOException e) {
            logger.error("Error reading users from JSON file for phone number check: {}", e.getMessage());
            throw e; // Rethrow the exception for handling at a higher level
        }

        for (User user : existingUsers) {
            if (user != null && user.getPhoneNumber() != null && user.getPhoneNumber().equals(phoneNumber)) {
                logger.info("Phone number {} is already registered.", phoneNumber);
                logger.info("Exiting isPhoneNumberAlreadyRegistered method (Phone number already registered)");
                return true;
            }
        }

        logger.info("Phone number {} is not registered.", phoneNumber);
        logger.info("Exiting isPhoneNumberAlreadyRegistered method (Phone number not registered)");

        return false;
    }
}
