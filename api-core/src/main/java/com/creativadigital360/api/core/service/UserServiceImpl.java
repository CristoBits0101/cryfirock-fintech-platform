package com.creativadigital360.api.core.service;

import com.creativadigital360.api.core.dto.UserUpdateDto;
import com.creativadigital360.api.core.entity.User;
import com.creativadigital360.api.core.mapper.UserMapper;
import com.creativadigital360.api.core.repository.JpaUserRepository;
import com.creativadigital360.api.core.util.PasswordUtils;
import com.creativadigital360.api.core.util.RolesUtils;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserServiceImpl implements IUserService {

    private final JpaUserRepository userRepository;

    private final RolesUtils rolesUtils;
    private final PasswordUtils passwordUtils;

    private final UserMapper userMapper;

    public UserServiceImpl(
            JpaUserRepository userRepository,
            RolesUtils rolesUtils,
            PasswordUtils passwordUtils,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.rolesUtils = rolesUtils;
        this.passwordUtils = passwordUtils;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public User save(@NotNull User user) {
        user.setRoles(rolesUtils.assignRoles(user));
        user.setPasswordHash(passwordUtils.encodeIfRaw(user.getPasswordHash()));
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
                .map(u -> {
                    u.setGivenName(user.getGivenName());
                    u.setFamilyName(user.getFamilyName());
                    u.setDob(user.getDob());

                    u.setEmail(user.getEmail());
                    u.setPhoneNumber(user.getPhoneNumber());
                    u.setAddress(user.getAddress());

                    u.setUsername(user.getUsername());

                    if (user.getPasswordHash() != null && !user.getPasswordHash().isBlank()) {
                        u.setPasswordHash(passwordUtils.encodeIfRaw(user.getPasswordHash()));
                    }

                    u.setRoles(rolesUtils.assignRoles(user));
                    u.setEnabled(user.getEnabled());

                    return userRepository.save(u);
                });
    }

    @Override
    @Transactional
    public Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto userDto) {
        return userRepository
                .findById(id)
                .map(u -> {
                    userMapper.update(u, userDto);

                    if (userDto.passwordHash() != null && !userDto.passwordHash().isBlank()) {
                        u.setPasswordHash(
                            passwordUtils.encodeIfRaw(
                                userDto.passwordHash()));
                    }

                    u.setRoles(rolesUtils.assignRoles(u));

                    return userRepository.save(u);
                });
    }

    @Override
    @Transactional
    public Optional<User> deleteById(@NotNull Long id) {
        return userRepository
                .findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                });
    }

}
