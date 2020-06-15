package com.tim04.school.facturing.user;

import com.tim04.school.facturing.persistence.role.Roles;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByMail(email);
    }


    @Transactional
    public User saveUser(@Valid User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Roles.ROLE_USER);
        return userRepository.save(user);
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    public User findLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUSer = auth.getName();
        User user = userRepository.findByMail(currentUSer);
        return user;
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
}
