package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public List<UserDTO> listAllUsers() {
       List<User> userList= userRepository.findAll(Sort.by("firstName"));
       return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());



    }

    @Override
    public UserDTO findByUserName(String username) {
        // userMapper.convertToDto(userRepository.findByUserName(username));
        User user= userRepository.findByUserName(username);
        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO user) {
     userRepository.save(userMapper.convertToEntity(user));//we don't use stream because is only one object
    }

    @Override
    public void deleteByUserName(String username) {
    userRepository.deleteByUserName(username);

    }

    @Override
    public UserDTO update(UserDTO user) {
        //find current user
        User user1=userRepository.findByUserName(user.getUserName());
        //map update user dto to entity object
        User convertedUser=userMapper.convertToEntity(user);
       //set id to the converted object
        convertedUser.setId(user1.getId());
        //save the update user in DB
        userRepository.save(convertedUser);
        return findByUserName(user.getUserName());

//      userRepository.save(userMapper.convertToEntity(user));
//      return findByUserName(user.getFirstName());


    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);//update and never delete

        //logic go to Db and het that user with username
        //change the isDeleted field dto true
        //save the object in db

    }
}
