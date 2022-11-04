package com.cydeo.mapper;

import org.modelmapper.ModelMapper;

public class BaseMapper <T, object>{

    private ModelMapper modelMapper;

    public BaseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
