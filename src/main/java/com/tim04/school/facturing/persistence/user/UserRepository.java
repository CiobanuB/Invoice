package com.tim04.school.facturing.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);
    User findByFirstName(String userName);
    List<User> findBymail(String email);

}
