package com.cryfirock.auth.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.cfg.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.helper.RolesHelper;
import com.cryfirock.auth.mapper.UserMapper;
import com.cryfirock.auth.repository.JpaUserRepository;
import com.cryfirock.auth.util.PasswordUtil;

import jakarta.validation.constraints.NotNull;

@Service
@Validated
public class UserServiceImpl implements IUserService {
  private final JpaUserRepository userRepository;
  private final RolesHelper rolesHelper;
  private final UserMapper userMapper;

  private Environment environment;

  public UserServiceImpl(
      JpaUserRepository userRepository,
      RolesHelper rolesHelper,
      UserMapper userMapper,
      Environment environment) {
    this.userRepository = userRepository;
    this.rolesHelper = rolesHelper;
    this.userMapper = userMapper;
    this.environment = environment;
  }

  @Override
  @Transactional
  public User save(@NotNull User user) {
    user.setRoles(rolesHelper.assignRoles(user));
    user.setPasswordHash(PasswordUtil.encodeIfRaw(user.getPasswordHash()));

    return Optional.ofNullable(user)
        .map(userRepository::save)
        .orElseThrow(() -> new IllegalArgumentException("User must not be null"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findById(@NotNull Long id) {
    return userRepository.findById(id);
  }

  @Override
  @Transactional
  public Optional<User> update(@NotNull Long id, @NotNull User user) {
    return userRepository
        .findById(id)
        .map(
            u -> {
              u.setGivenName(user.getGivenName());
              u.setFamilyName(user.getFamilyName());
              u.setDob(user.getDob());
              u.setEmail(user.getEmail());
              u.setPhoneNumber(user.getPhoneNumber());
              u.setAddress(user.getAddress());
              u.setUsername(user.getUsername());

              if (StringUtils.hasText(user.getPasswordHash()))
                u.setPasswordHash(
                    PasswordUtil.encodeIfRaw(
                        user.getPasswordHash()));

              u.setRoles(rolesHelper.assignRoles(user));
              u.setEnabled(user.getEnabled());
              return userRepository.save(u);
            });
  }

  @Override
  @Transactional
  public Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto userDto) {
    return userRepository
        .findById(id)
        .map(
            u -> {
              userMapper.update(u, userDto);

              if (userDto.passwordHash() != null && !userDto.passwordHash().isBlank())
                u.setPasswordHash(
                    PasswordUtil.encodeIfRaw(
                        userDto.passwordHash()));

              u.setRoles(rolesHelper.assignRoles(u));
              return userRepository.save(u);
            });
  }

  @Override
  @Transactional
  public Optional<User> deleteById(@NotNull Long id) {
    return userRepository
        .findById(id)
        .map(
            user -> {
              userRepository.delete(user);
              return user;
            });
  }
}
