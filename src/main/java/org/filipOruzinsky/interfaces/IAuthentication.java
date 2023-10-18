package org.filipOruzinsky.interfaces;

import java.io.IOException;

public interface IAuthentication {
    void runWithUserInput() throws IOException;
    void performLogin(String username, String password);
    void logout();
    boolean isValidCredentials (String username, String password) throws IOException;
    String getPhoneNumber();

}
