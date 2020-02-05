package com.tim04.school.facturing.user;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(String firstName,String lastName, String password, String mail, String role, int age)
    {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setMail(mail);
        user.setAge(age);
        user.setRole(role);
        userRepository.save(user);
    }
}
