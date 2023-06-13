package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {// SecurityService has userDetailsService
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }


//    @Bean //HARD CODED
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//        List<UserDetails> userList = new ArrayList<>();
//        //We are creating 2  new user and choose constructor wirth 3 parameters(including raw password) and grant Authority (Simple Grant Authority)
//        userList.add(new User("mike", encoder.encode("password")
//                , Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        userList.add(new User("ozzy", encoder.encode("password")
//                , Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));
//
//        return new InMemoryUserDetailsManager(userList);// we are saving our users in memory list hard coded
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {     // define filtering and overriding application
        return http
               .authorizeRequests()
//                .antMatchers("/user/**").hasRole("ADMIN")// need to be accessible by role under user controller
                  .antMatchers("task/**").hasAuthority("Admin")//hasAuthority (Admin-> Must match with DB Admin (spring is not putting prefix // m
                .antMatchers("/project/**").hasAuthority("Manager")// hasRole we need to put underscore in DB Role_Admin
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
               // .antMatchers("/task/**").hasAnyRole("EMPLOYEE, ADMIN")//hasAnyRole->more than one role
               // .antMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")// if we are using hasAuthority we need to use underscore
                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()// everyone can access to those pages
                .anyRequest().authenticated()// any other request needs to be authenticated
                .and()
            //    .httpBasic()// pop up box from spring // wee will change with our logIn page
                .formLogin()// my form
                .loginPage("/login")//navigate controller
               // .defaultSuccessUrl("/welcome")//successfully logIn by default  welcome/ /which page to land in the system as a user/ admin manager employee
                .successHandler(authSuccessHandler)//DI and pass authSuccessHandler
                .failureUrl("/login?error=true")//if authentication fail will go to this url
                .permitAll()//access for  everyone
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()//remember-me in html
                .tokenValiditySeconds(120)
                .key("cydeo")
                .userDetailsService(securityService)// remember who? we need DI Security service that has userDetailsService
                .and()
                .build();

    }
}