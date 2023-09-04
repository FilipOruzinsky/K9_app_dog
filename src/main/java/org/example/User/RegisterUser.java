package org.example.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Interfaces.IRegisterUser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RegisterUser implements IRegisterUser {

    private Map<String, User> registeredUsers;
    private ObjectMapper objectMapper;

    public RegisterUser(){
        registeredUsers = new HashMap<>();
        objectMapper = new ObjectMapper();
    }
    private static final Logger logger = LogManager.getLogger(RegisterUser.class);

 @Override
    public User registerUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please fill Registration Form");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter state: ");
        String state = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter dog breed: ");
        String dogBreed = scanner.nextLine();

        //tu som musel zavriet scanner aby po registracii mal user moznost loginu
//        scanner.close();
     User newUser = new User(firstName,lastName,address,state,phoneNumber,dogBreed);
     registeredUsers.put(newUser.getPhoneNumber(),newUser);

     if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
         logger.error("Registration failed: Required fields are missing.");
         return null;
     }else
         logger.info("Registration successful: New user created.");

     saveUsersToJsonFile();
     return newUser;



    }

    private void saveUsersToJsonFile() {
        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json"; // Specify the desired directory path
            objectMapper.writeValue(new File(filePath), registeredUsers);
            System.out.println("Users saved to JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving users to JSON file: " + e.getMessage());
        }
    }

    }

