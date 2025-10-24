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

/**
 * =================================================================================================================
 * Paso 9.1: Servicio para CRUD de usuarios
 * =================================================================================================================
 */
// Estereotipo que registra el bean en el contenedor y marca lógica de negocio
@Service
// Activa validación en parámetros y retornos de métodos públicos
@Validated
public class UserServiceImpl implements IUserService {

    /**
     * =============================================================================================================
     * Paso 9.2: Atributos
     * =============================================================================================================
     */
    // Repositorios de acceso a datos final
    private final JpaUserRepository userRepository;

    // Dependencias para roles y contraseñas
    private final RolesUtils rolesUtils;
    private final PasswordUtils passwordUtils;

    // Mapper para aplicar actualizaciones parciales
    private final UserMapper userMapper;

    /**
     * =============================================================================================================
     * Paso 9.3: Constructores
     * =============================================================================================================
     */
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

    /**
     * =============================================================================================================
     * Paso 9.4: Métodos create
     * =============================================================================================================
     */
    @Override
    @Transactional
    public User save(@NotNull User user) {
        user.setRoles(rolesUtils.assignRoles(user));
        user.setPasswordHash(passwordUtils.encodeIfRaw(user.getPasswordHash()));
        return Optional.ofNullable(user)
                .map(userRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("User must not be null"));
    }

    /**
     * =============================================================================================================
     * Paso 9.5: Métodos read
     * =============================================================================================================
     */
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

    /**
     * =============================================================================================================
     * Paso 9.6: Métodos update
     * =============================================================================================================
     */
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

    /**
     * =============================================================================================================
     * Paso 9.7: Métodos delete
     * =============================================================================================================
     */
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
