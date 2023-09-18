package org.example;

import org.example.user.RegisterUser;
import org.example.user.User;
import org.example.user.UserLogin;

import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the app");

        Locale currentLocale = selectLanguage(scanner);
        ResourceBundle formBundle = ResourceBundle.getBundle("messages", currentLocale);

        while (true) {
            System.out.println(formBundle.getString("menu.choose.option")); // Choose an option

            System.out.println(formBundle.getString("menu.option.register"));
            System.out.println(formBundle.getString("menu.option.login"));
            System.out.println(formBundle.getString("menu.option.exit"));

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    RegisterUser registerUser = new RegisterUser(currentLocale);
                    User newUser = registerUser.registerUser();

                    if (newUser != null) {
                        System.out.println("Registration was successful.");
                        System.out.println("Do you want to log in now? (yes/no): ");
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
                    break;

                case 2:
                    UserLogin userLogin = new UserLogin();
                    userLogin.runWithUserInput();
                    System.out.println("You are successfully logged in");
                    if (userLogin != null) {
                        System.out.println(userLogin);
                        while (true) {
                            System.out.println("Do you want to continue? (yes/no): ");
                            String continueChoice = scanner.nextLine().trim().toLowerCase();
                            if (continueChoice.equals("yes")) {
                                System.out.println("You can do whatever you want now");
                                break; // Exit the loop if the user chooses "yes"
                            } else if (continueChoice.equals("no")) {
                                while (true) {
                                    System.out.println("Do you want to leave the app? (yes/no): ");
                                    String leaveChoice = scanner.nextLine().trim().toLowerCase();
                                    if (leaveChoice.equals("yes")) {
                                        System.out.println("Exiting the application. Goodbye!");
                                        System.exit(0);
                                    } else if (leaveChoice.equals("no")) {
                                        System.out.println("Returning to the main menu.");
                                        break; // Exit the inner loop if the user chooses "no"
                                    } else {
                                        System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                                    }
                                }
                                break; // Exit the outer loop
                            } else {
                                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option");
            }
        }

    }

    private static Locale selectLanguage(Scanner scanner) {
        System.out.println("Choose your language: ");
        System.out.println("1. English");
        System.out.println("2. German");
        System.out.println("3. French");

        int languageChoice;
        if (scanner.hasNextInt()) {
            languageChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } else {
            System.out.println("Invalid input. Using default (English)...");
            scanner.nextLine(); // Consume invalid input
            return Locale.ENGLISH;
        }

        switch (languageChoice) {
            case 1:
                return Locale.ENGLISH;
            case 2:
                return Locale.GERMAN;
            case 3:
                return Locale.FRENCH;
            default:
                System.out.println("Invalid choice. Using default (English)...");
                return Locale.ENGLISH;
        }
    }
}
