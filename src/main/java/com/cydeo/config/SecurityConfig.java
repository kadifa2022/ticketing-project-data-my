package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        List<UserDetails> userList = new ArrayList<>();
        //We are creating 2  new user and choose constructor wirth 3 parameters(including raw password) and grant Authority (Simple Grant Authority)
        userList.add(new User("mike", encoder.encode("password")
                , Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        userList.add(new User("ozzy", encoder.encode("password")
                , Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));

        return new InMemoryUserDetailsManager(userList);// we are saving our users in memory list hard coded
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {     // define filtering and overriding application
        return http
                .authorizeRequests()
                .antMatchers("/user/**").hasRole("ADMIN")// need to be accessible by role under user controller
                .antMatchers("/project/**").hasRole("MANAGER")
                .antMatchers("/task/employee/**").hasRole("EMPLOYEE")
                .antMatchers("/task/**").hasRole("MANAGER")
                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()// everyone can access to those pages
                .anyRequest().authenticated()// any other request needs to be authenticated
                .and()
                .httpBasic()// pop up box from spring // wee will change with our logIn page
                .and().build();

    }
}