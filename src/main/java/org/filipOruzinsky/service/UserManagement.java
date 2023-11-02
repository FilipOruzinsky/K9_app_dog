package org.filipOruzinsky.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.dataAcces.UserDataAccess;
import org.filipOruzinsky.interfaces.IUserManagement;
import org.filipOruzinsky.user.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserManagement extends User implements IUserManagement {


    public UserManagement(Locale currentLocale){
        this.currentLocale = currentLocale;
        registeredUsers = new HashMap<>();
        security = new Security();
    };
    public UserManagement(){};

    private Security security;



    private static final Logger logger = LogManager.getLogger(UserManagement.class);

    private Locale currentLocale;


    public Map<String, User> registeredUsers;



    public User registerUser() throws IOException {
        logger.info("Entering registerUser method");

        Scanner scanner = new Scanner(System.in);
        ResourceBundle formBundle = ResourceBundle.getBundle("messages", currentLocale);

        System.out.println(formBundle.getString("fill_registration_form"));

        String firstName = "";
        while (firstName.isEmpty()) {
            System.out.print(formBundle.getString("enter_first_name"));
            firstName = scanner.nextLine();
            logger.debug("User input - First Name: {}", firstName);

            if (firstName.isEmpty()) {
                System.out.println(formBundle.getString("first_name_required"));
            }
        }

        String lastName = "";
        while (lastName.isEmpty()) {
            System.out.print(formBundle.getString("enter_last_name"));
            lastName = scanner.nextLine();
            logger.debug("User input - Last Name: {}", lastName);

            if (lastName.isEmpty()) {
                System.out.println(formBundle.getString("last_name_required"));
            }
        }


        String password = "";
        while (true) {
            System.out.println(formBundle.getString("enter_password"));
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println(formBundle.getString("password_required"));
            } else if (!security.isValidPassword(password)) {
                System.out.println(formBundle.getString("password_invalid"));
                System.out.println(formBundle.getString("password_requirements"));
            } else {
                break; // Valid password, exit the loop
            }
        }


        String address = "";
        while (address.isEmpty()) {
            System.out.print(formBundle.getString("enter_address"));
            address = scanner.nextLine();
            logger.debug("User input - Address: {}", address);

            if (address.isEmpty()) {
                System.out.println(formBundle.getString("address_required"));
            }
        }

        String state = "";
        while (state.isEmpty()) {
            System.out.print(formBundle.getString("enter_state"));
            state = scanner.nextLine();
            logger.debug("User input - State: {}", state);

            if (state.isEmpty()) {
                System.out.println(formBundle.getString("state_required"));
            }
        }

        String phoneNumber = "";
        while (phoneNumber.isEmpty()) {
            System.out.print(formBundle.getString("enter_phone_number"));
            phoneNumber = scanner.nextLine();
            logger.debug("User input - Phone Number: {}", phoneNumber);

            Security security = new Security();
            if (phoneNumber.isEmpty()) {
                System.out.println(formBundle.getString("phone_number_required"));
            } else if (security.isPhoneNumberAlreadyRegistered(phoneNumber)) {
                System.out.println(formBundle.getString("phone_number_already_exists"));
                logger.error("Registration failed: Phone number already exists.");
                return null; // Registration failed
            }
        }

        String dogBreed = "";
        while (dogBreed.isEmpty()) {
            System.out.print(formBundle.getString("enter_dog_breed"));
            dogBreed = scanner.nextLine();
            logger.debug("User input - Dog Breed: {}", dogBreed);

            if (dogBreed.isEmpty()) {
                System.out.println(formBundle.getString("dog_breed_required"));
            }
        }

        User newUser = new User(firstName, lastName, password, address, state, phoneNumber, dogBreed);
        registeredUsers.put(newUser.getPhoneNumber(), newUser);

        logger.info("Registration successful: New user created.");
        try {
            UserDataAccess userDataAccess = new UserDataAccess();
            userDataAccess.saveUsersToJsonFile(registeredUsers);
            return  newUser;
        } catch (IOException e) {
        }
        logger.info("Exiting registerUser method ");
        return  newUser;
    }

    public static User getUserByName(String filePath, String firstName) throws IOException {
        logger.info("Entering getUserByName method with filePath: {} and firstName: {}", filePath, firstName);

        UserManagement registerUser = new UserManagement();
        List<User> existingUsers = registerUser.readUsersFromJsonFile(filePath);

        logger.debug("Number of existing users loaded from file: {}", existingUsers.size());

        for (User user : existingUsers) {
            if (user.getFirstName().equalsIgnoreCase(firstName)) {
                logger.info("Found a user with the matching name: {}", firstName);
                logger.info("Exiting getUserByName method (Success)");
                return user; // Found a user with the matching name (case-insensitive)
            }
        }

        logger.info("No user found with the name: {}", firstName);

        logger.info("Exiting getUserByName method (No user found)");

        return null; // User not found
    }
    public List<User> readUsersFromJsonFile(String filePath) throws IOException {
        logger.info("Entering readUsersFromJsonFile method for filePath: {}", filePath);

        ObjectMapper objectMapper1 = new ObjectMapper();
        File file = new File(filePath);

        List<User> users;

        try {
            users = objectMapper1.readValue(file, new TypeReference<List<User>>() {});
            logger.info("Users successfully read from JSON file.");
        } catch (IOException e) {
            logger.error("Error reading users from JSON file: {}", e.getMessage());
            throw e; // Rethrow the exception for handling at a higher level
        }

        logger.info("Exiting readUsersFromJsonFile method");

        return users;
    }
    public void editUserInfoByPhoneNumber(String phoneNumber) {
        logger.info("Entering editUserInfoByPhoneNumber method for phone number: {}", phoneNumber);

        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json"; // Provide the correct file path

            // Call the readUsersFromJsonFile method to get the list of existing users
            List<User> existingUsers = readUsersFromJsonFile(filePath);

            // Check if the phone number is already registered
            Security security = new Security();
            if (!security.isPhoneNumberAlreadyRegistered( phoneNumber)) {
                System.out.println("User with phone number " + phoneNumber + " not found.");
                // Log that the user with the given phone number was not found
                logger.info("User with phone number {} not found.", phoneNumber);
                return;
            }

            // Find the user by phone number
            User userToEdit = null;
            for (User user : existingUsers) {
                if (user != null && user.getPhoneNumber() != null && user.getPhoneNumber().equals(phoneNumber)){
                    userToEdit = user;
                    break;
                }
            }

            Scanner scanner = new Scanner(System.in);

            System.out.println("Select the information you want to edit for user " + phoneNumber + ":");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Password");
            System.out.println("4. Address");
            System.out.println("5. State");
            System.out.println("6. Phone Number");
            System.out.println("7. Dog Breed");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new first name: ");
                    String newFirstName = scanner.nextLine();
                    userToEdit.setFirstName(newFirstName);

                    logger.info("User has set a new first name: {}", newFirstName);
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.nextLine();
                    userToEdit.setLastName(newLastName);

                    logger.info("User has set a new last name: {}", newLastName);
                    break;
                case 3:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    userToEdit.setPassword(newPassword);

                    logger.info("User has set a new password.");
                    break;
                case 4:
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    userToEdit.setAddress(newAddress);

                    logger.info("User has set a new address: {}", newAddress);
                    break;
                case 5:
                    System.out.print("Enter new state: ");
                    String newState = scanner.nextLine();
                    userToEdit.setState(newState);

                    logger.info("User has set a new state: {}", newState);
                    break;
                case 6:
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    userToEdit.setPhoneNumber(newPhoneNumber);

                    logger.info("User has set a new phone number: {}", newPhoneNumber);
                    break;
                case 7:
                    System.out.print("Enter new dog breed: ");
                    String newDogBreed = scanner.nextLine();
                    userToEdit.setDogBreed(newDogBreed);

                    logger.info("User has set a new dog breed: {}", newDogBreed);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    logger.warn("Invalid choice made for editing user information.");
                    return;
            }

            // Save the edited user information back to the JSON file
            UserDataAccess userDataAccess = new UserDataAccess();
            userDataAccess.saveEditedUserToJsonFile(filePath, existingUsers);
            System.out.println("Information updated successfully for user " + phoneNumber + ".");
            logger.info("Information updated successfully for user {}.", phoneNumber);
        } catch (IOException e) {
            logger.error("Error editing user information: {}", e.getMessage());
            System.out.println("Error editing user information: " + e.getMessage());
        }

        logger.info("Exiting editUserInfoByPhoneNumber method");
    }

}
