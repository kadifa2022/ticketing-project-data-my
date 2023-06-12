package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    //private final User

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // this method get the user from DB and convert to user spring Understands
    // we are converting  with the mapper UserPrinciple class

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);// we returned new user principle with new user
    }
}
