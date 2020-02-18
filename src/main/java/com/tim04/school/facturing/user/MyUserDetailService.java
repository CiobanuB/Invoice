package com.tim04.school.facturing.user;

import com.tim04.school.facturing.persistence.Role.Role;
import com.tim04.school.facturing.persistence.Role.RoleRepository;
import com.tim04.school.facturing.persistence.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException { // this method is implicit called by security plugin
        User user = userService.findUserByEmail(mail);
        if (null == user) {
            System.out.println("User not found:" + mail);
            throw new UsernameNotFoundException("User not found:" + mail);
        }
        System.out.println(mail + " is found in database!");
        List<GrantedAuthority> grantedList = new ArrayList<>();

        for (Role role : user.getRoles()) {
            grantedList.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getMail(),
                user.getPassword(), grantedList);

        return userDetails;
    }
}
