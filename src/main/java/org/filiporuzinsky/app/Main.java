package org.filiporuzinsky.app;

import org.filiporuzinsky.admin.Admin;
import org.filiporuzinsky.service.RegisterUser;
import org.filiporuzinsky.user.User;
import org.filiporuzinsky.service.UserLogin;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the app");

        Locale currentLocale = selectLanguage(scanner);
        ResourceBundle formBundle = ResourceBundle.getBundle("messages", currentLocale);

        while (true) {
            menuLoop: while (true) {
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

                        RegisterUser registerUser1 = new RegisterUser();
                        List<User> users = registerUser1.readUsersFromJsonFile("/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json");

                        // Check if the logged-in user is an admin
                        boolean isAdmin = isUserAdmin(users, userLogin.getFirstName());

                        while (true) {
                            System.out.println("Do you want to continue? (yes/no): ");
                            String continueChoice = scanner.nextLine().trim().toLowerCase();
                            if (continueChoice.equals("yes")) {
                                System.out.println("You can do whatever you want now");
                                while (true) {
                                    System.out.println("1. Logout from app");
                                    System.out.println("2. Exit the application");
                                    System.out.println("3. Edit your information");
                                    System.out.println("4. Return to the main menu");

                                    if (isAdmin) {
                                        System.out.println("5. Delete user(admin only)");
                                    }
                                    System.out.print("Choose an option: ");
                                    String innerChoice = scanner.nextLine().trim();
                                    switch (innerChoice) {
                                        case "1":
                                            // User chose to log out
                                            userLogin.logout(); // Implement the logout method in UserLogin
                                            System.out.println("You have been logged out.");
                                            break; // Exit the current case
                                        case "2":
                                            // User chose to exit the whole app
                                            System.out.println("Exiting the application. Goodbye!");
                                            System.exit(0);
                                            break; // Exit the current case
                                        case "3":
                                            System.out.println(userLogin.getPhoneNumber());
                                            if (userLogin.getPhoneNumber() != null) {
                                                RegisterUser registerUser2 = new RegisterUser(currentLocale);
                                                String phoneNumber = userLogin.getPhoneNumber();
                                                registerUser2.editUserInfoByPhoneNumber(phoneNumber);
                                            } else {
                                                System.out.println("User not logged in. Please log in first");
                                            }
                                            break;
                                        case "4":
                                            break menuLoop;
                                        case "5":
                                            if (isAdmin) {
                                                Admin admin = new Admin();
                                                if (admin.getFirstName().equals("admin"))

                                                System.out.print("Enter user's phone number to delete: ");
                                                String phoneNumberToDelete = scanner.nextLine();
                                                if (admin.deleteUserByPhoneNumber(phoneNumberToDelete)) {
                                                    System.out.println("User with phone number " + phoneNumberToDelete + " has been deleted.");
                                                } else {
                                                    System.out.println("User deletion failed.");
                                                }
                                            } else {
                                                System.out.println("Admin login required to delete users.");
                                            }
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5");
                                            break;
                                    }
                                }
                            } else if (continueChoice.equals("no")) {
                                while (true) {
                                    System.out.println("Do you want to leave the app? (yes/no): ");
                                    String leaveChoice = scanner.nextLine().trim().toLowerCase();
                                    if (leaveChoice.equals("yes")) {
                                        System.out.println("Exiting the application. Goodbye!");
                                        System.exit(0);
                                    } else if (leaveChoice.equals("no")) {
                                        System.out.println("Returning to the main menu.");
                                        break;
                                    } else {
                                        System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                                    }
                                }
                                break;
                            } else {
                                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
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


private static boolean isUserAdmin(List<User> users, String firstName) {
    for (User user : users) {
        if ("admin".equals(user.getFirstName()) && "admin".equals(user.getPassword()) && "admin".equals(firstName)) {
            System.out.println("Admin user found.");
            return true;
        }
    }
    return false;
}

}
