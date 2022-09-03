package com.example.restcrudsample.service;

import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import com.example.restcrudsample.model.dto.UserPatchNameDTO;

public interface UserService {
    User create(UserDTO userDTO);

    User retrieve(Long id);

    User update(Long id, UserDTO userDTO);

    User patch(Long id, UserPatchNameDTO userPatchNameDTO);

    void delete(Long id);

}
