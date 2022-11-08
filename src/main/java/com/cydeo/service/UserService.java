package com.cydeo.service;

import com.cydeo.dto.UserDTO;


import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO user);
//    void deleteByUserName(String username);
    UserDTO update(UserDTO user);
    void delete(String username);//from entity--to userService not delete in db go to impl.
    List<UserDTO> listAllByRole(String role);





}
