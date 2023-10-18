package org.filipOruzinsky.interfaces;

import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.Map;

public interface IUserDataAccess {
    void saveUsersToJsonFile(Map<String,User>registeredUsers) throws IOException;

}
