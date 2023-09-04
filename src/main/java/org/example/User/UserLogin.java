package org.example.User;


import org.example.Interfaces.IUserLogin;
import org.example.MyLogger;

import java.util.Scanner;

public class UserLogin implements IUserLogin {

@Override
    public void runWithUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        scanner.close();

        if (isValidCredentials(username, password)) {
            performLogin(username,password);
        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }


    @Override
    public boolean isValidCredentials(String username, String password) {
        // always accept password
        return true;
    }

    @Override
    public void performLogin(String username, String password) {
        System.out.println("Logging in user :" + username );
       MyLogger.info("Logging in : username " + username + " password " + password);
        MyLogger.error("eror");
//        MyLogger.info("jojo");
        MyLogger.debug("ja som debug");
        MyLogger.fatal("jojooj");





    }
//    private static final Logger logger = LogManager.getLogger(UserLogin.class);
}
