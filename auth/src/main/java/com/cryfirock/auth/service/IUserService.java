package com.cryfirock.auth.service;

import java.util.List;
import java.util.Optional;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;

import jakarta.validation.constraints.NotNull;

public interface IUserService {
  User save(@NotNull User user);

  List<User> findAll();

  Optional<User> findById(@NotNull Long id);

  Optional<User> update(@NotNull Long id, @NotNull User user);

  Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto dto);

  Optional<User> deleteById(@NotNull Long id);
}
