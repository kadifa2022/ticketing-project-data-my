package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();//bring all roles and return type//what needs to go UI

    RoleDTO findById(Long id);

}