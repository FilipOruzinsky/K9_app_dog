package org.filipOruzinsky.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.filipOruzinsky.interfaces.IDeleteUser;
import org.filipOruzinsky.service.RegisterUser;
import org.filipOruzinsky.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Admin extends RegisterUser implements IDeleteUser {
    private static final Logger logger = LogManager.getLogger(Admin.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public Admin(Locale currentLocale) {
        super(currentLocale);
    }
    public Admin(){}
    private String firstName= "admin";


    public String getFirstName() {
        return firstName;
    }


@Override
public boolean deleteUserByPhoneNumber(String phoneNumber) {
    // Log entry point: Start of the deleteUserByPhoneNumber method
    logger.info("Entering deleteUserByPhoneNumber method for phone number: {}", phoneNumber);

    try {
        String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json";
        RegisterUser registerUser = new RegisterUser();

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
            // Log that the user with the given phone number was not found
            logger.info("User with phone number {} not found.", phoneNumber);
            return false; // User not found, deletion failed
        }

        objectMapper.writeValue(new File(filePath), existingUsers);

        // Log that the user with the given phone number has been deleted successfully
        logger.info("User with phone number {} deleted successfully.", phoneNumber);

        System.out.println("User with phone number " + phoneNumber + " deleted successfully.");
        return true;
    } catch (IOException e) {
        // Log that an error occurred while deleting the user
        logger.error("Error deleting user: {}", e.getMessage());

        System.out.println("Error deleting user: " + e.getMessage());
        return false;
    }
}

}
