package org.example.admin;

import org.example.interfaces.IDeleteUser;
import org.example.user.RegisterUser;
import org.example.user.User;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Admin extends RegisterUser implements IDeleteUser  {

    @Override
    public boolean deleteUserByPhoneNumber(String phoneNumber) {
        try {
            String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json";
            RegisterUser registerUser = new RegisterUser();

            List<User>existingUser = registerUser.readUsersFromJsonFile(filePath);


        boolean userFound = false;
        Iterator<User>iterator = existingUser.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if (user.getPhoneNumber().equals(phoneNumber)){
                iterator.remove();
                userFound = true;
                break;
            }
        }
            if (!userFound) {
                System.out.println("User with phone number " + phoneNumber + " not found.");
                return false; // User not found, deletion failed
            }
            objectMapper.writeValue(new File(filePath), existingUser);
            System.out.println("User with phone number " + phoneNumber + " deleted successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }

    }
}
