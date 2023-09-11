package org.example;

import org.example.admin.Admin;
import org.example.user.RegisterUser;
import org.example.user.User;
import org.example.user.UserLogin;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        RegisterUser registerUser = new RegisterUser();
        String phoneNumberToEdit = "lll";
        registerUser.editUserInfoByPhoneNumber(phoneNumberToEdit);

// funkcna metoda na zmazanie usera
//        Admin admin = new Admin();
//        String phoneNumberToDelete = "bb";
//        admin.deleteUserByPhoneNumber(phoneNumberToDelete);


//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome into app");
//
//
//        RegisterUser registerUser = new RegisterUser();
//        User newUser = registerUser.registerUser();
//
//        if (newUser != null) {
//
//            System.out.println("Registrácia prebehla úspešne. Teraz sa môžete prihlásiť.");
//
//            UserLogin userLogin = new UserLogin();
//            userLogin.runWithUserInput();
//
//
//        } else {
//            System.out.println("Registrácia zlyhala. Skúste to znova.");
//        }
//
//        scanner.close();
//        }


    }
}



