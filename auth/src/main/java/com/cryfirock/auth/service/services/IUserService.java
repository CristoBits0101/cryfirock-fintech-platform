package com.cryfirock.auth.service.services;

import java.util.List;
import java.util.Optional;

import com.cryfirock.auth.service.entities.User;

// Limits the actions available in the application
public interface IUserService {

    // Custom methods for validating
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // Métodos CRUD
    List<User> findAll();

    Optional<User> deleteUser(User user);

    Optional<User> findById(Long id);

    Optional<User> update(Long id, User user);

    User save(User user);

}