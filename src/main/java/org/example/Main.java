package org.example;

import org.example.User.RegisterUser;
import org.example.User.User;
import org.example.User.UserLogin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

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


