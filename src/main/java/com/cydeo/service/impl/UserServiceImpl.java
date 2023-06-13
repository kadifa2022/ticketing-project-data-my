package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;

    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {
       List<User> userList= userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
       return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());



    }

    @Override
    public UserDTO findByUserName(String username) {
        // userMapper.convertToDto(userRepository.findByUserName(username));
        User user= userRepository.findByUserNameAndIsDeleted(username, false);
        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO user) {

        user.setEnabled(true);

        //before saving in DB we need to encode password
        User obj = userMapper.convertToEntity(user);
        obj.setPassWord(passwordEncoder.encode(obj.getPassWord()));
        userRepository.save(obj);//we don't use stream because is only one object
    }

//    @Override
//    public void deleteByUserName(String username) {
//    userRepository.deleteByUserName(username);

//    }

    @Override
    public UserDTO update(UserDTO user) {
        //find current user
        User user1=userRepository.findByUserNameAndIsDeleted(user.getUserName(), false);
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
        User user = userRepository.findByUserNameAndIsDeleted(username,false);

        if(checkIfUserCanBeDeleted(user)) { //condition
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());//harold@manager.com-2
            userRepository.save(user);//from user service update and never delete

            //logic go to Db and het that user with username
            //change the isDeleted field dto true
            //save the object in db
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role, false);

        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user) {

        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.
                        listAllNonCompletedByAssignedManager(userMapper.convertToDto(user));
                return projectDTOList.size() == 0;//true
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOList.size() == 0;
            default:
                return true;
        }

    }
}
