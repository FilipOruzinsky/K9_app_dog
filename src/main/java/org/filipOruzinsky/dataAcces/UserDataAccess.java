package org.filipOruzinsky.dataAcces;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.interfaces.IUserDataAccess;
import org.filipOruzinsky.service.Authentication;
import org.filipOruzinsky.service.UserManagement;
import org.filipOruzinsky.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDataAccess  implements IUserDataAccess {

    private static final Logger logger = LogManager.getLogger(UserDataAccess.class);
    public ObjectMapper objectMapper;
    public UserDataAccess(){
        objectMapper = new ObjectMapper();
    }


    public void saveUsersToJsonFile(Map<String,User>registeredUsers) throws IOException {
        // Log entry point: Start of the saveUsersToJsonFile method
        logger.info("Entering saveUsersToJsonFile method");

        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json"; // Specify the desired directory path
            UserManagement userManagement = new UserManagement();
            List<User> existingUsers = userManagement.readUsersFromJsonFile(filePath);
            existingUsers.addAll(new ArrayList<>(registeredUsers.values())); // Use the provided registeredUsers parameter
            objectMapper.writeValue(new File(filePath), existingUsers);
            System.out.println(existingUsers);
            System.out.println("Users saved to JSON file."); // Consider replacing this with a log message
            // Log that users have been successfully saved to the JSON file
            logger.info("Users saved to JSON file.");

            // Handle potential FileNotFound or other IOExceptions here
        } catch (IOException e) {
            // Log that an error occurred while saving users to the JSON file
            logger.error("Error saving users to JSON file: {}", e.getMessage(), e);
            System.out.println("Error saving users to JSON file: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Caught General exception", e);
        }

        // Log entry point: End of the saveUsersToJsonFile method
        logger.info("Exiting saveUsersToJsonFile method");
    }

    public void saveEditedUserToJsonFile(String filePath, List<User> usersToSave) {
        // Log entry point: Start of the saveEditedUserToJsonFile method
        logger.info("Entering saveEditedUserToJsonFile method for filePath: {}", filePath);

        try {
            objectMapper.writeValue(new File(filePath), usersToSave);

            // Log that user information has been successfully saved to the JSON file
            logger.info("User information saved to JSON file.");

            System.out.println("User information saved to JSON file.");
        } catch (IOException e) {
            // Log that an error occurred while saving user information to the JSON file
            logger.error("Error saving user information to JSON file: {}", e.getMessage());

            System.out.println("Error saving user information to JSON file: " + e.getMessage());
        }

        // Log entry point: End of the saveEditedUserToJsonFile method
        logger.info("Exiting saveEditedUserToJsonFile method");
    }
}
