package org.filipOruzinsky.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.filipOruzinsky.interfaces.IRegisterUser;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class RegisterUser implements IRegisterUser {
    private static final Logger logger = LogManager.getLogger(RegisterUser.class);
    //ObjectMapper provides serialization and deserialization can covertJson data to Java object
    public ObjectMapper objectMapper;
    private Locale currentLocale;
    private Map<String, User> registeredUsers;
//    public RegisterUser registerUser = new RegisterUser();

    public RegisterUser(Locale currentLocale) {
        this.currentLocale = currentLocale;
        registeredUsers = new HashMap<>();
        objectMapper = new ObjectMapper();
    }

    public RegisterUser() {

    }

    public static User getUserByName(String filePath, String firstName) throws IOException {
        // Log entry point: Start of the getUserByName method
        logger.info("Entering getUserByName method with filePath: {} and firstName: {}", filePath, firstName);

        RegisterUser registerUser = new RegisterUser();
        List<User> existingUsers = registerUser.readUsersFromJsonFile(filePath);

        // Log the number of existing users loaded from the file
        // size() , represent how many users exist in existingUsers object
        logger.debug("Number of existing users loaded from file: {}", existingUsers.size());

        for (User user : existingUsers) {
            if (user.getFirstName().equalsIgnoreCase(firstName)) {
                // Log that a user with a matching name has been found
                logger.info("Found a user with the matching name: {}", firstName);
                // Log entry point: End of the getUserByName method (success)
                logger.info("Exiting getUserByName method (Success)");
                return user; // Found a user with the matching name (case-insensitive)
            }
        }

        // Log that no user with the given name was found
        logger.info("No user found with the name: {}", firstName);

        // Log entry point: End of the getUserByName method (no user found)
        logger.info("Exiting getUserByName method (No user found)");

        return null; // User not found
    }


    @Override
    public User registerUser() throws IOException {
        // Log entry point: Start of the registerUser method
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
        while (password.isEmpty()) {
            System.out.println(formBundle.getString("enter_password"));
            password = scanner.nextLine();
            // Add validation for the password
            if (password.isEmpty()) {
                System.out.println(formBundle.getString("password_required"));
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

            if (phoneNumber.isEmpty()) {
                System.out.println(formBundle.getString("phone_number_required"));
            } else if (isPhoneNumberAlreadyRegistered(phoneNumber)) {
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

        // Log that registration was successful and a new user was created
        logger.info("Registration successful: New user created.");
// TODO make return code if save method working or not OR create own exception
        try {
            saveUsersToJsonFile();
            return newUser;
        } catch (IOException e) {
            System.out.println("tusom");
        }
        logger.info("Exiting registerUser method ");
        //ked je exception naco na returne mam usera ?
        return  newUser;
    }




    public void saveUsersToJsonFile() throws IOException{
        // Log entry point: Start of the saveUsersToJsonFile method
        logger.info("Entering saveUsersToJsonFile method");

        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json"; // Specify the desired directory path
            List<User> existingUsers = readUsersFromJsonFile(filePath);
            existingUsers.addAll(new ArrayList<>(registeredUsers.values()));
            objectMapper.writeValue(new File(filePath), existingUsers);
            System.out.println("Users saved to JSON file."); // Consider replacing this with a log message
            // Log that users have been successfully saved to the JSON file
            logger.info("Users saved to JSON file.");

            //ked nemoze ulozit chyti exception
            //FIleNOtFoundException
        } catch (IOException e) {

            // Log that an error occurred while saving users to the JSON file
            logger.error("Error saving users to JSON file: {}", e.getMessage(),e);
            System.out.println("Error saving users to JSON file: " + e.getMessage());

        }catch (Exception e){
            logger.error(" Caught General exception ");
        }

        // Log entry point: End of the saveUsersToJsonFile method
//        logger.info("Exiting saveUsersToJsonFile method");
    }


    public List<User> readUsersFromJsonFile(String filePath) throws IOException {
        // Log entry point: Start of the readUsersFromJsonFile method
        logger.info("Entering readUsersFromJsonFile method for filePath: {}", filePath);

        ObjectMapper objectMapper1 = new ObjectMapper();
        File file = new File(filePath);

        List<User> users;

        try {
            users = objectMapper1.readValue(file, new TypeReference<List<User>>() {});
            // Log that users have been successfully read from the JSON file
            logger.info("Users successfully read from JSON file.");
        } catch (IOException e) {
            // Log that an error occurred while reading users from the JSON file
            logger.error("Error reading users from JSON file: {}", e.getMessage());
            throw e; // Rethrow the exception for handling at a higher level
        }

        // Log entry point: End of the readUsersFromJsonFile method
        logger.info("Exiting readUsersFromJsonFile method");

        return users;
    }


    public boolean isPhoneNumberAlreadyRegistered(String phoneNumber) throws IOException {
        // Log entry point: Start of the isPhoneNumberAlreadyRegistered method
        logger.info("Entering isPhoneNumberAlreadyRegistered method for phone number: {}", phoneNumber);

        List<User> existingUsers=new ArrayList<>();

        try {
            existingUsers = readUsersFromJsonFile("/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json");
            // Log that users have been successfully read from the JSON file
            logger.debug("Users successfully read from JSON file for phone number check.");
        } catch (IOException e) {
            // Log that an error occurred while reading users from the JSON file
            logger.error("Error reading users from JSON file for phone number check: {}", e.getMessage());
            throw e; // Rethrow the exception for handling at a higher level
        }

        for (User user : existingUsers) {
            if (user != null && user.getPhoneNumber() != null && user.getPhoneNumber().equals(phoneNumber)) {
                // Log that the phone number is already registered
                logger.info("Phone number {} is already registered.", phoneNumber);
                // Log entry point: End of the isPhoneNumberAlreadyRegistered method (phone number already registered)
                logger.info("Exiting isPhoneNumberAlreadyRegistered method (Phone number already registered)");
                return true;
            }
        }

        // Log that the phone number is not registered
        logger.info("Phone number {} is not registered.", phoneNumber);

        // Log entry point: End of the isPhoneNumberAlreadyRegistered method (phone number not registered)
        logger.info("Exiting isPhoneNumberAlreadyRegistered method (Phone number not registered)");

        return false;
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


    public void editUserInfoByPhoneNumber(String phoneNumber) {
        // Log entry point: Start of the editUserInfoByPhoneNumber method
        logger.info("Entering editUserInfoByPhoneNumber method for phone number: {}", phoneNumber);

        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json"; // Provide the correct file path

            // Call the readUsersFromJsonFile method to get the list of existing users
            List<User> existingUsers = readUsersFromJsonFile(filePath);

            // Check if the phone number is already registered
            if (!isPhoneNumberAlreadyRegistered( phoneNumber)) {
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

                    // Log that the user has set a new first name
                    logger.info("User has set a new first name: {}", newFirstName);
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.nextLine();
                    userToEdit.setLastName(newLastName);

                    // Log that the user has set a new last name
                    logger.info("User has set a new last name: {}", newLastName);
                    break;
                case 3:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    userToEdit.setPassword(newPassword);

                    // Log that the user has set a new password
                    logger.info("User has set a new password.");
                    break;
                case 4:
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    userToEdit.setAddress(newAddress);

                    // Log that the user has set a new address
                    logger.info("User has set a new address: {}", newAddress);
                    break;
                case 5:
                    System.out.print("Enter new state: ");
                    String newState = scanner.nextLine();
                    userToEdit.setState(newState);

                    // Log that the user has set a new state
                    logger.info("User has set a new state: {}", newState);
                    break;
                case 6:
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    userToEdit.setPhoneNumber(newPhoneNumber);

                    // Log that the user has set a new phone number
                    logger.info("User has set a new phone number: {}", newPhoneNumber);
                    break;
                case 7:
                    System.out.print("Enter new dog breed: ");
                    String newDogBreed = scanner.nextLine();
                    userToEdit.setDogBreed(newDogBreed);

                    // Log that the user has set a new dog breed
                    logger.info("User has set a new dog breed: {}", newDogBreed);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    // Log that an invalid choice was made
                    logger.warn("Invalid choice made for editing user information.");
                    return;
            }

            // Save the edited user information back to the JSON file
            saveEditedUserToJsonFile(filePath, existingUsers);
            System.out.println("Information updated successfully for user " + phoneNumber + ".");
            // Log that the user information has been updated successfully
            logger.info("Information updated successfully for user {}.", phoneNumber);
        } catch (IOException e) {
            // Log that an error occurred while editing user information
            logger.error("Error editing user information: {}", e.getMessage());
            System.out.println("Error editing user information: " + e.getMessage());
        }

        // Log entry point: End of the editUserInfoByPhoneNumber method
        logger.info("Exiting editUserInfoByPhoneNumber method");
    }

}






