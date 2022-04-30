package com.example.restcrudsample.model.mapper;

import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User dtoToUser(UserDTO userDTO);
}
