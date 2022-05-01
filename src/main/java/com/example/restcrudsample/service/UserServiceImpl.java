package com.example.restcrudsample.service;

import com.example.restcrudsample.exception.BusinessException;
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
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public User retreive(Long id) {

        validateId(id);

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

        validateId(id);
        validateDTO(userDTO);

        User user = UserMapper.INSTANCE.dtoToUser(userDTO);
        user.setId(id);

        return userRepository.save(user);
    }

    public User patch(Long id, UserPatchNameDTO userPatchNameDTO) {

        validateId(id);

        if (!StringUtils.hasText(userPatchNameDTO.getName()))
            throw new BusinessException("User Name can not be Empty");

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
            throw new BusinessException("User can not be Empty");

        if (!StringUtils.hasText(userDTO.getName()))
            throw new BusinessException("User Name can not be Empty");

        if (userDTO.getBirth() == null)
            throw new BusinessException("User Birth Date can not be Empty");
    }

    private void validateId(Long id) {

        if (id == null)
            throw new BusinessException("Id can not be Empty");

        if (id <= 0)
            throw new BusinessException("Id can not be less or equals to Zero");
    }
}
