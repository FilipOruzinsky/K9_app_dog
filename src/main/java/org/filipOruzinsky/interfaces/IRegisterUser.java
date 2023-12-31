package org.filipOruzinsky.interfaces;

import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.List;

public interface IRegisterUser {

    User registerUser() throws IOException;
    void saveUsersToJsonFile() throws IOException;
    List<User> readUsersFromJsonFile(String filePath) throws IOException;
    boolean isPhoneNumberAlreadyRegistered(String phoneNumber) throws IOException;
    void saveEditedUserToJsonFile(String filePath, List<User> usersToSave) throws IOException;
    void editUserInfoByPhoneNumber(String phoneNumber) throws IOException;








}
