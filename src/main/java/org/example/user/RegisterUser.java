package org.example.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.interfaces.IRegisterUser;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class RegisterUser implements IRegisterUser {
    private Locale currentLocale;

    private static final Logger logger = LogManager.getLogger(RegisterUser.class);
    private Map<String, User> registeredUsers;
    public ObjectMapper objectMapper;

    public RegisterUser() {
        registeredUsers = new HashMap<>();
        objectMapper = new ObjectMapper();
    }

    @Override
    public User registerUser() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your language:  ");
        System.out.println("1. English");
        System.out.println("2. German");
        System.out.println("3. French");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                currentLocale = Locale.ENGLISH;
                break;
            case 2:
                currentLocale = Locale.GERMAN;
                break;
            case 3:
                currentLocale = Locale.FRENCH;
                break;
            default:
                System.out.println("Invalid choice. Using default (English)...");
                currentLocale = Locale.ENGLISH;
        }
        ResourceBundle formBundle = ResourceBundle.getBundle("messages", currentLocale);
        scanner.nextLine();

        System.out.println(formBundle.getString("fill_registration_form"));
        System.out.print(formBundle.getString("enter_first_name"));
        String firstName = scanner.nextLine();

        System.out.print(formBundle.getString("enter_last_name"));
        String lastName = scanner.nextLine();

        System.out.println(formBundle.getString("enter_password"));
        String password = scanner.nextLine();

        System.out.print(formBundle.getString("enter_address"));
        String address = scanner.nextLine();

        System.out.print(formBundle.getString("enter_state"));
        String state = scanner.nextLine();

        System.out.print(formBundle.getString("enter_phone_number"));
        String phoneNumber = scanner.nextLine();
        // check if phone number exist if yes user canot register because phone number is uniqe
        if (isPhoneNumberAlreadyRegistered(phoneNumber)) {
            System.out.println(formBundle.getString("phone_number_already_exists"));
            return null; // Registration failed
        }

        System.out.print(formBundle.getString("enter_dog_breed"));
        String dogBreed = scanner.nextLine();


        User newUser = new User(firstName, lastName, password, address, state, phoneNumber, dogBreed);
        registeredUsers.put(newUser.getPhoneNumber(), newUser);

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
            logger.error("Registration failed: Required fields are missing.");
            return null;
        } else
            logger.info("Registration successful: New user created.");

        saveUsersToJsonFile();
        return newUser;
    }


    public void saveUsersToJsonFile() {
        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json"; // Specify the desired directory path
            List<User> existingUsers = readUsersFromJsonFile(filePath);
            existingUsers.addAll(new ArrayList<>(registeredUsers.values()));
            objectMapper.writeValue(new File(filePath), existingUsers);
            System.out.println("User saved to JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving users to JSON file: " + e.getMessage());
        }
    }

    public List<User> readUsersFromJsonFile(String filePath) throws IOException {
        List<User> existingUsers = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()) {
            existingUsers = objectMapper.readValue(file, new TypeReference<List<User>>() {
            });
        }

        return existingUsers;
    }

    private boolean isPhoneNumberAlreadyRegistered(String phoneNumber) throws IOException {
        List<User> existingUsers = readUsersFromJsonFile("/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json");

        for (User user : existingUsers) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
    //TODO skontrolovat ci edituje a sejvuje usera do user.json
    public void saveEditedUserToJsonFile(String filePath, List<User> usersToSave) {
        try {
            objectMapper.writeValue(new File(filePath), usersToSave);
            System.out.println("User information saved to JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving user information to JSON file: " + e.getMessage());
        }
    }

    public void editUserInfoByPhoneNumber(String phoneNumber) {
        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json"; // Provide the correct file path

            // Call the readUsersFromJsonFile method to get the list of existing users
            List<User> existingUsers = readUsersFromJsonFile(filePath);

            // Check if the phone number is already registered
            if (!isPhoneNumberAlreadyRegistered(existingUsers, phoneNumber)) {
                System.out.println("User with phone number " + phoneNumber + " not found.");
                return;
            }

            // Find the user by phone number
            User userToEdit = null;
            for (User user : existingUsers) {
                if (user.getPhoneNumber().equals(phoneNumber)) {
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
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.nextLine();
                    userToEdit.setLastName(newLastName);
                    break;
                case 3:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    userToEdit.setPassword(newPassword);
                    break;
                case 4:
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();
                    userToEdit.setAddress(newAddress);
                    break;
                case 5:
                    System.out.print("Enter new state: ");
                    String newState = scanner.nextLine();
                    userToEdit.setState(newState);
                    break;
                case 6:
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    userToEdit.setPhoneNumber(newPhoneNumber);
                    break;
                case 7:
                    System.out.print("Enter new dog breed: ");
                    String newDogBreed = scanner.nextLine();
                    userToEdit.setDogBreed(newDogBreed);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            // Save the edited user information back to the JSON file
            saveEditedUserToJsonFile(filePath, existingUsers);
            System.out.println("Information updated successfully for user " + phoneNumber + ".");
        } catch (IOException e) {
            System.out.println("Error editing user information: " + e.getMessage());
        }
    }

    public boolean isPhoneNumberAlreadyRegistered(List<User> existingUsers, String phoneNumber) {
        for (User user : existingUsers) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }








}






