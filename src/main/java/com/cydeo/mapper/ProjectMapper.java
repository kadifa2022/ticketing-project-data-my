package com.cydeo.mapper;

import com.cydeo.converter.ProjectDtoConverter;
import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private final ModelMapper modelMapper;                //solid principle do not crete all in one class-- create methode whatever we need use

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Project convertToEntity(ProjectDTO dto) {

        return modelMapper.map(dto, Project.class);//entity class

    }

    public ProjectDTO convertToDto(Project entity) {
        return modelMapper.map(entity, ProjectDTO.class);
    }
}


