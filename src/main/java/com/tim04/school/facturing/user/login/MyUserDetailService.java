package com.tim04.school.facturing.user.login;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException, LockedException { // this method is implicit called by security plugin
        User user = userService.findUserByEmail(mail);
        if (null == user || !user.getActive()) {
            System.out.println("User not found:" + mail);
            throw new UsernameNotFoundException("User not found:" + mail);
        }

        System.out.println(mail + " is found in database!");
        List<GrantedAuthority> grantedList = new ArrayList<>();

        grantedList.add(new SimpleGrantedAuthority(user.getRoles().toString()));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getMail(),
                user.getPassword(), grantedList);

        return userDetails;
    }
}
