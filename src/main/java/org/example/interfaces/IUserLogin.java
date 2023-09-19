package org.example.interfaces;

import java.io.IOException;

public interface IUserLogin {
    void runWithUserInput() throws IOException;
    boolean isValidCredentials (String username, String password) throws IOException;
    void performLogin(String username, String password);
    void logout();
    String getPhoneNumber();




}

