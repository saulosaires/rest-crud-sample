package com.example.restcrudsample.service;

import com.example.restcrudsample.exception.user.UserException;
import com.example.restcrudsample.exception.user.UserNotFoundException;
import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import com.example.restcrudsample.model.dto.UserPatchNameDTO;
import com.example.restcrudsample.model.mapper.UserMapper;
import com.example.restcrudsample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User retreive(Long id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty())
            throw new UserNotFoundException(id);

        return optUser.get();
    }

    public User create(UserDTO userDTO) {

        validateDTO(userDTO);

        User user = UserMapper.INSTANCE.dtoToUser(userDTO);

        return userRepository.save(user);
    }

    public User update(Long id, UserDTO userDTO) {

        if (id == null)
            throw new UserException("Id can not be Empty");

        validateDTO(userDTO);

        User user = UserMapper.INSTANCE.dtoToUser(userDTO);
        user.setId(id);

        return userRepository.save(user);
    }

    public User patch(Long id, UserPatchNameDTO userPatchNameDTO) {

        if (id == null)
            throw new UserException("Id can not be Empty");

        if (!StringUtils.hasText(userPatchNameDTO.getName()))
            throw new UserException("User Name can not be Empty");

        User user = retreive(id);
        user.setId(id);
        user.setName(userPatchNameDTO.getName());

        return userRepository.save(user);
    }

    public void delete(Long id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty())
            throw new UserNotFoundException(id);

        userRepository.delete(optUser.get());
    }

    private void validateDTO(UserDTO userDTO) {

        if (userDTO == null)
            throw new UserException("User can not be Empty");

        if (!StringUtils.hasText(userDTO.getName()))
            throw new UserException("User Name can not be Empty");

        if (userDTO.getBirth() == null)
            throw new UserException("User Birth Date can not be Empty");
    }
}
