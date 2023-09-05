package org.example;

import org.example.User.RegisterUser;
import org.example.User.User;
import org.example.User.UserLogin;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        Scanner scanner2 = new Scanner(System.in);
//        Locale currentLocale = Locale.ENGLISH;
//        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
//        System.out.println("Choose your language:  ");
//        System.out.println("1. English");
//        System.out.println("2. German");
//        System.out.println("3. French");
//        System.out.println("4. Russian");
//        int choice = scanner2.nextInt();
//
//        switch (choice) {
//            case 1:
//                currentLocale = Locale.ENGLISH;
//                break;
//            case 2:
//                currentLocale = Locale.GERMAN;
//                break;
//            case 3:
//                currentLocale = Locale.FRENCH;
//                break;
//            case 4:
//                currentLocale = new Locale("ru", "RU");
//                break;
//            default:
//                System.out.println("Invalid choice. Using default (English)...");
//        }
//        messages = ResourceBundle.getBundle("messages", currentLocale);
//        String greeting = messages.getString("greeting");
//        System.out.println(greeting);
//
//        scanner2.close();
//    }
//}

// toto je fungujuci stary kod madafaka

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome into app");


        RegisterUser registerUser = new RegisterUser();
        User newUser = registerUser.registerUser();

        if (newUser != null) {

            System.out.println("Registrácia prebehla úspešne. Teraz sa môžete prihlásiť.");

            UserLogin userLogin = new UserLogin();
            userLogin.runWithUserInput();


        } else {
            System.out.println("Registrácia zlyhala. Skúste to znova.");
        }

        scanner.close();
        }


    }



