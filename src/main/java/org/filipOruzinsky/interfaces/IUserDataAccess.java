package org.filipOruzinsky.interfaces;

import org.filipOruzinsky.user.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IUserDataAccess {
    void saveUsersToJsonFile(Map<String,User>registeredUsers) throws IOException;
    void saveEditedUserToJsonFile(String filePath, List<User>userToSave);

}
