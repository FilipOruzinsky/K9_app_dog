package org.filipOruzinsky.repository;

import org.filipOruzinsky.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByFirstNameAndLastName(String firstName ,String lastName);
}
