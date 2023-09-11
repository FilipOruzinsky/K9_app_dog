package org.example.user;


import org.example.interfaces.IUserLogin;
import org.example.MyLogger;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserLogin implements IUserLogin {

    private RegisterUser registerUser = new RegisterUser();

    public UserLogin(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }
    public UserLogin(){};

    String filePath = "/home/fo/IdeaProjects/k9_app/src/main/java/org/example/users.json";


    @Override
    public void runWithUserInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please fill your credentials");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        scanner.close();

        if (isValidCredentials(username, password)) {
            performLogin(username, password);
        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }


    @Override
    public boolean isValidCredentials(String firstName, String password) throws IOException {
        User user = registerUser.getUserByName(filePath,firstName);
        if (user != null && user.getPassword().equals(password)) {
            return true; // Username and password match
        }
        return false; // Invalid credentials

    // always accept password

}

    @Override
    public void performLogin(String username, String password) {
        System.out.println("Logging in user :" + username );
       MyLogger.info("Logging in : username " + username + " password " + password);

    }


}
