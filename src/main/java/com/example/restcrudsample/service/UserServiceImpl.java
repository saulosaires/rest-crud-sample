package com.example.restcrudsample.service;

import com.example.restcrudsample.exception.BusinessException;
import com.example.restcrudsample.exception.user.UserNotFoundException;
import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import com.example.restcrudsample.model.dto.UserPatchNameDTO;
import com.example.restcrudsample.model.mapper.UserMapper;
import com.example.restcrudsample.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public User retreive(Long id) {

        validateId(id);
        log.info("retreive user {}", keyValue("userId", id));

        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty())
            throw new UserNotFoundException(id);

        return optUser.get();
    }

    public User create(UserDTO userDTO) {

        validateDTO(userDTO);

        User user = userRepository.save(UserMapper.INSTANCE.dtoToUser(userDTO));

        log.info("created user {}", keyValue("userId", user.getId()));

        return user;
    }

    public User update(Long id, UserDTO userDTO) {

        validateId(id);
        validateDTO(userDTO);

        User user = userRepository.save(UserMapper.INSTANCE.parse(id, userDTO));

        log.info("updated user {}", keyValue("userId", user.getId()));

        return user;
    }

    public User patch(Long id, UserPatchNameDTO userPatchNameDTO) {

        validateId(id);

        if (!StringUtils.hasText(userPatchNameDTO.getName()))
            throw new BusinessException("User Name can not be Empty");

        User user = retreive(id);
        user.setId(id);
        user.setName(userPatchNameDTO.getName());

        userRepository.save(user);

        log.info("patched user {} {}", keyValue("userId", user.getId()), keyValue("userName", user.getName()));

        return user;
    }

    public void delete(Long id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty())
            throw new UserNotFoundException(id);

        userRepository.delete(optUser.get());

        log.info("deleted user {}", keyValue("userId", id));
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
