package org.example.Interfaces;

public interface IUserLogin {
    boolean isValidCredentials (String username,String password);
    void performLogin(String username, String password);

    void runWithUserInput();


}

