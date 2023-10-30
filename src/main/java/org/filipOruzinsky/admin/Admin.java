package org.filipOruzinsky.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.interfaces.IAdmin;
import org.filipOruzinsky.service.UserManagement;
import org.filipOruzinsky.user.User;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Admin extends UserManagement implements IAdmin {
    private static final Logger logger = LogManager.getLogger(Admin.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private String firstName ="admin";
    private String password = "admin";

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean deleteUserByPhoneNumber(String phoneNumber) {
        logger.info("Entering deleteUserByPhoneNumber method for phone number: {}", phoneNumber);

        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json";
            UserManagement registerUser = new UserManagement();

            // Call the readUsersFromJsonFile method to get the list of existing users
            List<User> existingUsers = registerUser.readUsersFromJsonFile(filePath);

            boolean userFound = false;
            Iterator<User> iterator = existingUsers.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                if (user != null && user.getPhoneNumber() != null && user.getPhoneNumber().equals(phoneNumber)) {
                    iterator.remove();
                    userFound = true;
                    break;
                }
            }

            if (!userFound) {
                System.out.println("User with phone number " + phoneNumber + " not found.");
                logger.info("User with phone number {} not found.", phoneNumber);
                return false; // User not found, deletion failed
            }

            objectMapper.writeValue(new File(filePath), existingUsers);

            logger.info("User with phone number {} deleted successfully.", phoneNumber);

            System.out.println("User with phone number " + phoneNumber + " deleted successfully.");
            return true;
        } catch (IOException e) {
            logger.error("Error deleting user: {}", e.getMessage());

            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

}
