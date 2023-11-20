package org.filipOruzinsky.repository;

import org.filipOruzinsky.repository.UserRepository;
import org.filipOruzinsky.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   public User getUserByName(String firstName, String lastName){
        User user = userRepository.findByFirstNameAndLastName(firstName,lastName);
        return user;
   }

    public  void saveUserToDB(User user){
        userRepository.save(user);
   }

}

