package org.filipOruzinsky.interfaces;

import java.io.IOException;

public interface ISecurity {
    boolean isPhoneNumberAlreadyRegistered(String phoneNumber) throws IOException;
    boolean isValidPassword(String password);
}
