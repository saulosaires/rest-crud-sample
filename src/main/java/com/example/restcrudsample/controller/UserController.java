package com.example.restcrudsample.controller;

import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import com.example.restcrudsample.model.dto.UserPatchNameDTO;
import com.example.restcrudsample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User retreive(@PathVariable Long id) {
        return userService.retreive(id);
    }

    @PostMapping()
    User create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping("/{id}")
    User update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @PatchMapping("/{id}")
    User patch(@PathVariable Long id, @RequestBody UserPatchNameDTO userPatchNameDTO) {
        return userService.patch(id, userPatchNameDTO);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
