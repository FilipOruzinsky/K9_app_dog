package org.filipOruzinsky.interfaces;

import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.List;

public interface IUserManagement {
    User registerUser()throws IOException;
    List<User> readUsersFromJsonFile(String filePath) throws IOException;
    void editUserInfoByPhoneNumber(String phoneNumber) throws IOException;

}
