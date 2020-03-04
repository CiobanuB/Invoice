package com.tim04.school.facturing.user;

import com.tim04.school.facturing.persistence.Role.Role;
import com.tim04.school.facturing.persistence.Role.RoleRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByMail(email);
    }

    public User findUserByFirstName(String userName) {
        return userRepository.findByFirstName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User findLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUSer = auth.getName();
        User user = userRepository.findByMail(currentUSer);

        return user;
    }
}
