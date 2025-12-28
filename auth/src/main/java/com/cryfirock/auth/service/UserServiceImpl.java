package com.cryfirock.auth.service;

import java.util.List;
import java.util.Optional;

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

/**
 * Implementación del servicio de usuarios.
 * Proporciona operaciones CRUD y lógica de negocio para la gestión de usuarios.
 * 
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025
 */
@Service
@Validated
public class UserServiceImpl implements IUserService {
  /**
   * Atributos
   */
  private final JpaUserRepository userRepository;
  private final RolesHelper rolesHelper;
  private final UserMapper userMapper;

  public UserServiceImpl(
      JpaUserRepository userRepository,
      RolesHelper rolesHelper,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.rolesHelper = rolesHelper;
    this.userMapper = userMapper;
  }

  /**
   * Guarda un nuevo usuario en la base de datos.
   * Asigna roles automáticamente y encripta la contraseña.
   * 
   * @param user objeto del usuario con detalles del nuevo usuario.
   * @return el usuario guardado.
   */
  @Override
  @Transactional
  public User save(@NotNull User user) {
    user.setRoles(rolesHelper.assignRoles(user));
    user.setPasswordHash(PasswordUtil.encodeIfRaw(user.getPasswordHash()));
    return Optional.ofNullable(user)
        .map(userRepository::save)
        .orElseThrow(() -> new IllegalArgumentException("User must not be null"));
  }

  /**
   * Recupera todos los usuarios de la base de datos.
   * 
   * @return lista de usuarios.
   */
  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return userRepository.findAll();
  }

  /**
   * Busca un usuario por su ID.
   * 
   * @param id el identificador único del usuario.
   * @return un Optional que contiene el usuario si existe.
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<User> findById(@NotNull Long id) {
    return userRepository.findById(id);
  }

  /**
   * Actualiza la información de un usuario existente.
   * 
   * @param id el identificador único del usuario.
   * @param user los nuevos datos del usuario.
   * @return un Optional que contiene el usuario actualizado.
   */
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
                u.setPasswordHash(PasswordUtil.encodeIfRaw(user.getPasswordHash()));

              u.setRoles(rolesHelper.assignRoles(user));
              u.setEnabled(user.getEnabled());
              return userRepository.save(u);
            });
  }

  /**
   * Actualiza un usuario usando un DTO.
   * 
   * @param id el identificador único del usuario.
   * @param userDto objeto con los datos a actualizar.
   * @return un Optional que contiene el usuario actualizado.
   */
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
