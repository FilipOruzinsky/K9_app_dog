package org.example.interfaces;

import java.io.IOException;

public interface IUserLogin {
    boolean isValidCredentials (String username,String password) throws IOException;
    void performLogin(String username, String password);

    void runWithUserInput() throws IOException;


}

