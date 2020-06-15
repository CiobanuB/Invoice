package com.tim04.school.facturing.user.login;

import com.tim04.school.facturing.persistence.role.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUser implements UserDetails {
    private final String username;
    private final String password;
    private final Roles roles;

    public MyUser(String username, String password, Roles roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedList = new ArrayList<>();
        grantedList.add(new SimpleGrantedAuthority(this.roles.toString()));
        return grantedList;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
