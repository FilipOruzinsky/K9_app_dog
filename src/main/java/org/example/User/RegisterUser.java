package org.example.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Interfaces.IRegisterUser;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class RegisterUser implements IRegisterUser {
    private  Locale currentLocale;

    private static final Logger logger = LogManager.getLogger(RegisterUser.class);
    private Map<String, User> registeredUsers;
    private ObjectMapper objectMapper;

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
        ResourceBundle formBundle = ResourceBundle.getBundle("messages",currentLocale);
        scanner.nextLine();

        System.out.println(formBundle.getString("fill_registration_form"));
        System.out.print(formBundle.getString("enter_first_name"));
        String firstName = scanner.nextLine();

        System.out.print(formBundle.getString("enter_last_name"));
        String lastName = scanner.nextLine();

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


        User newUser = new User(firstName, lastName, address, state, phoneNumber, dogBreed);
        registeredUsers.put(newUser.getPhoneNumber(), newUser);

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
            logger.error("Registration failed: Required fields are missing.");
            return null;
        } else
            logger.info("Registration successful: New user created.");

        saveUsersToJsonFile();
        return newUser;
    }


    private void saveUsersToJsonFile() {
        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json"; // Specify the desired directory path
            List<User>existingUsers = readUsersFromJsonFile(filePath);
            existingUsers.addAll(new ArrayList<>(registeredUsers.values()));
               objectMapper.writeValue(new File(filePath),existingUsers);
            System.out.println("User saved to JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving users to JSON file: " + e.getMessage());
        }
    }
    private List<User> readUsersFromJsonFile(String filePath) throws IOException {
        List<User> existingUsers = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()) {
            existingUsers = objectMapper.readValue(file, new TypeReference<List<User>>() {});
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



}






