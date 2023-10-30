package org.filipOruzinsky.interfaces;

import java.io.IOException;
import java.util.Locale;

public interface IAuthentication {
    boolean runWithUserInput(Locale currentLocale) throws IOException;

    void performLogin(String username, String password, Locale currentLocale);

    void logout(Locale currentLocale);

    boolean isValidCredentials(String username, String password, Locale currentLocale) throws IOException;

    String getPhoneNumber();

}
