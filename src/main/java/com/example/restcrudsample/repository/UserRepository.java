package com.example.restcrudsample.repository;

import com.example.restcrudsample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

