package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {//with this clas we are changing .defaultSuccessUrl in form


    @Override// what user can access
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //giving authorizations to each role where they can land on the page after logIn   // user can have more than one role
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("Admin")){// checking th role user//
            response.sendRedirect("/user/create");
        }
        if(roles.contains("Manager")){
            response.sendRedirect("/project/create");
        }
        if(roles.contains("Employee")){
            response.sendRedirect("/task/employee/pending-tasks");
        }
    }
}
