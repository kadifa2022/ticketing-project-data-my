package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// user principal class  will use mapping for me , and making contracts with  UserDetails interface
// need to override all methods from DB and set to spring security fields what is spring looking for
public class UserPrincipal implements UserDetails {// UserPrincipal is mapper  now I need to override SecurityService interface extendDetailsService interface

    private final User user;// DI my Entity

    public UserPrincipal(User user) {//constructor
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // this is what spring understand GrantedAuthority
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //we are defining role
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());// getting role descriptions from DB ->ADMIN, MANAGER, EMPLOYEE
        authorityList.add(authority);
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassWord();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {// we don't have this field in entity just put true
        return true;
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
        return this.user.isEnabled();
    }
}
