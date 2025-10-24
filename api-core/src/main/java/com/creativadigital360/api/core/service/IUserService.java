package com.creativadigital360.api.core.service;

import com.creativadigital360.api.core.dto.UserUpdateDto;
import com.creativadigital360.api.core.entity.User;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User save(@NotNull User user);

    List<User> findAll();

    Optional<User> findById(@NotNull Long id);

    Optional<User> update(@NotNull Long id, @NotNull User user);

    Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto dto);

    Optional<User> deleteById(@NotNull Long id);

}
