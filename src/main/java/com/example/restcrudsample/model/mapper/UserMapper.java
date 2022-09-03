package com.example.restcrudsample.model.mapper;

import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User dtoToUser(UserDTO userDTO);

    default User parse(Long id, UserDTO userDTO) {

        User user = dtoToUser(userDTO);
        user.setId(id);

        return user;
    }

}
