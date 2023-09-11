package org.example;

import org.example.user.RegisterUser;
import org.example.user.User;
import org.example.user.UserLogin;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome into app");

        while (true) {
            System.out.println("Choose an option");
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    RegisterUser registerUser = new RegisterUser();
                    User newUser = registerUser.registerUser();

                    if (newUser != null) {

                        System.out.println("Registration was successful.");
                        System.out.println("Do you want to log in now ?(yes/no): ");
                        String loginChoice = scanner.nextLine().trim().toLowerCase();
                        if (loginChoice.equals("yes")) {
                            UserLogin userLogin = new UserLogin();
                            userLogin.runWithUserInput();
                            System.out.println("Logged in successfully!");
                        } else {
                            System.out.println("Returning to the main menu.");
                        }

                    } else {
                        System.out.println("Registration failed. Please try again.");
                    }

                    scanner.close();
                    break;

                case 2:
                    UserLogin userLogin = new UserLogin();
                    userLogin.runWithUserInput();
                    System.out.println("You are successfully logged in");
                    break;

                case 3:
                    System.out.println("Exiting the application.Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.Please enter a valid option");

            }
            System.out.println("Do you want to continue? (yes/no): ");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            if (!continueChoice.equals("yes")){
                System.out.println("Exiting the application.Goodbye!");
                scanner.close();
                System.exit(0);
            }

        }
    }


}




