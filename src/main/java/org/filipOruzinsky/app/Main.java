package org.filipOruzinsky.app;

import org.filipOruzinsky.admin.Admin;
import org.filipOruzinsky.service.Authentication;
import org.filipOruzinsky.service.UserManagement;
import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.filipOruzinsky.user.User.selectLanguage;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to the app");
        Scanner scanner = new Scanner(System.in);

        Locale currentLocale = selectLanguage(scanner);
        ResourceBundle formBundle = ResourceBundle.getBundle("messages", currentLocale);

        mainMenuLoop: while (true) {
            registrationLoop: while (true) {
                System.out.println(formBundle.getString("menu.choose.option")); // Choose an option

                System.out.println(formBundle.getString("menu.option.register"));
                System.out.println(formBundle.getString("menu.option.login"));
                System.out.println(formBundle.getString("menu.option.exit"));

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        UserManagement userManagement = new UserManagement(currentLocale);
                        User newUser = userManagement.registerUser();

                        if (newUser != null) {
                            System.out.println(formBundle.getString("registration_message"));

                            System.out.println(formBundle.getString("login_offer"));
                            String loginChoice = scanner.nextLine().trim().toLowerCase();

                            if (loginChoice.equals("yes") || loginChoice.equals("ja") || loginChoice.equals("qui")) {
                                Authentication authentication = getAuthentication(currentLocale, formBundle);
                                System.out.println(formBundle.getString("loggedin_message2"));


                                loggedInMenuLoop: while (true) {
                                    System.out.println(formBundle.getString("continue_offer"));
                                    String continueChoice = scanner.nextLine().trim().toLowerCase();
                                    if (continueChoice.equals("yes") || continueChoice.equals("ja") || continueChoice.equals("qui")) {
                                        System.out.println(formBundle.getString("user_can_choose"));

                                        while (true) {
                                            System.out.println(formBundle.getString("logout_message"));
                                            System.out.println(formBundle.getString("exit_message"));
                                            System.out.println(formBundle.getString("edit_information"));
                                            System.out.println(formBundle.getString("return_to_menu"));
                                            String innerChoice = scanner.nextLine().trim();
                                            switch (innerChoice) {
                                                case "1":
                                                    authentication.logout(currentLocale);
                                                    System.out.println(formBundle.getString("logout_message_for_user"));
                                                    break;
                                                case "2":
                                                    System.out.println(formBundle.getString("exit_app_message_for_user"));
                                                    System.exit(0);
                                                    break;
                                                case "3":
                                                    String phoneNumber = newUser.getPhoneNumber();
                                                    UserManagement userManagement1 = new UserManagement();
                                                    userManagement1.editUserInfoByPhoneNumber(phoneNumber);
                                                    break;
                                                case "4":
                                                    break loggedInMenuLoop; // Return to the logged-in user menu
                                            }
                                        }
                                    } else {
                                        System.out.println(formBundle.getString("main_menu_return"));
                                        break registrationLoop; // Return to the registration menu
                                    }
                                }
                            }
                        } else {
                            System.out.println(formBundle.getString("main_menu_return"));
                            break registrationLoop; // Return to the registration menu
                        }
                        break;
                    case 2:
                        Authentication authentication = getAuthentication(currentLocale, formBundle);
                        UserManagement registerUser1 = new UserManagement();
                        List<User> users = registerUser1.readUsersFromJsonFile("/home/fo/IdeaProjects/k9_app/src/main/java/org/filipOruzinsky/users.json");
                        boolean isAdmin = isUserAdmin(users, authentication.getFirstName());
                        System.out.println(formBundle.getString("loggedin_message2"));


                        loggedInMenuLoop: while (true) {
                            System.out.println(formBundle.getString("continue_offer"));
                            String continueChoice = scanner.nextLine().trim().toLowerCase();
                            if (continueChoice.equals("yes") || continueChoice.equals("ja") || continueChoice.equals("qui")) {
                                System.out.println(formBundle.getString("user_can_choose"));
                                while (true) {
                                    System.out.println(formBundle.getString("logout_message"));
                                    System.out.println(formBundle.getString("exit_message"));
                                    System.out.println(formBundle.getString("edit_information"));
                                    System.out.println(formBundle.getString("return_to_menu"));

                                    if (isAdmin) {
                                        System.out.println("5. Delete user (admin only)");
                                    }

                                    String innerChoice = scanner.nextLine().trim();
                                    switch (innerChoice) {
                                        case "1":
                                            authentication.logout(currentLocale);
                                            System.out.println(formBundle.getString("logout_message_for_user"));
                                            break;
                                        case "2":
                                            System.out.println(formBundle.getString("exit_app_message_for_user"));
                                            System.exit(0);
                                            break;
                                        case "3":
                                            System.out.println(authentication.getPhoneNumber() + "phoneNumber");
                                            if (authentication.getPhoneNumber() != null) {
                                                UserManagement registerUser2 = new UserManagement(currentLocale);
                                                String phoneNumber = String.valueOf(authentication.getPhoneNumber());
                                                registerUser2.editUserInfoByPhoneNumber(phoneNumber);
                                            } else {
                                                System.out.println("login_first_message");
                                            }
                                            break;
                                        case "4":
                                            break loggedInMenuLoop; // Return to the logged-in user menu
                                        case "5":
                                            if (isAdmin) {
                                                Admin admin = new Admin();
                                                if (admin.getFirstName().equals("admin")) {
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
                                            }
                                            break;
                                        default:
                                            System.out.println(formBundle.getString("invalid_choice"));
                                            break;
                                    }
                                }
                            } else if (continueChoice.equals("no") || continueChoice.equals("nein") || continueChoice.equals("non")) {
                                while (true) {
                                    System.out.println("leave_offer");
                                    String leaveChoice = scanner.nextLine().trim().toLowerCase();
                                    if (leaveChoice.equals("yes") || leaveChoice.equals("ja") || leaveChoice.equals("qui")) {
                                        System.out.println(formBundle.getString("exit_message2"));
                                        System.exit(0);
                                    } else if (leaveChoice.equals("no") || leaveChoice.equals("nein") || leaveChoice.equals("non")) {
                                        System.out.println(formBundle.getString("return_to_menu2"));
                                        break loggedInMenuLoop; // Return to the logged-in user menu
                                    } else {
                                        System.out.println(formBundle.getString("invalid_choice2"));
                                    }
                                }
                            } else {
                                System.out.println(formBundle.getString("invalid_choice2"));
                            }
                        }
                        break;
                    case 3:
                        System.out.println(formBundle.getString("exit_message2"));
                        System.exit(0);
                    default:
                        System.out.println(formBundle.getString("invalid_choice3"));
                        break;
                }
            }
        }
    }

    private static Authentication getAuthentication(Locale currentLocale, ResourceBundle formBundle) throws IOException {
        System.out.println(formBundle.getString("enter_credentials"));
        Authentication authentication = new Authentication();
        boolean isUserLogin;
        do {
            isUserLogin = authentication.runWithUserInput(currentLocale);
            if (!isUserLogin) {
                System.out.println(formBundle.getString("logginNot_message"));
            }
        } while (!isUserLogin);
        return authentication;
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
