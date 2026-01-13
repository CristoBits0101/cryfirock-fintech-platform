package com.cryfirock.auth.service.application;

import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.helper.RolesHelper;
import com.cryfirock.auth.mapper.UserMapper;
import com.cryfirock.auth.repository.JpaUserRepository;
import com.cryfirock.auth.service.contract.IUserService;
import com.cryfirock.auth.util.PasswordUtil;

import jakarta.validation.constraints.NotNull;

/**
 * 1. Se ocupa de las operaciones relacionadas con los usuarios.
 * 2. Implementa la interfaz IUserService.
 * 3. Utiliza JpaUserRepository para interactuar con la base de datos.
 * 4. Proporciona métodos para CRUD de usuarios.
 */
@Service
@Validated
public class UserServiceImpl implements IUserService {
  // Repositorio JPA para usuarios.
  private final JpaUserRepository userRepository;
  // Helper para asignar roles a los usuarios.
  private final RolesHelper rolesHelper;
  // Mapper para convertir entre entidades y DTOs de usuario.
  private final UserMapper userMapper;
  // Entorno de configuración de Spring.
  private final Environment environment;

  /**
   * Constructor que inyecta las dependencias necesarias.
   * 
   * @param userRepository
   * @param rolesHelper
   * @param userMapper
   * @param environment
   */
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

  /**
   * 1. Guarda un nuevo usuario en la base de datos.
   * 2. Asigna roles al usuario utilizando RolesHelper.
   * 3. Codifica la contraseña si es necesario.
   * 4. Lanza IllegalArgumentException si el usuario es nulo.
   * 
   * @param user Usuario a guardar.
   * @return Usuario guardado.
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
   * 1. Recupera todos los usuarios de la base de datos.
   * 2. Establece el puerto del servidor en cada usuario.
   * 3. Utiliza el entorno de Spring para obtener el puerto.
   * 4. Marca la transacción como de solo lectura.
   * 
   * @return Lista de todos los usuarios.
   */
  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return userRepository
        .findAll()
        .stream()
        .peek(user -> user
            .setPort(Integer
                .parseInt(environment
                    .getProperty("local.server.port"))))
        .toList();
  }

  /**
   * 1. Busca un usuario por su ID.
   * 2. Marca la transacción como de solo lectura.
   * 3. Suprime las advertencias de nulidad.
   * 4. Devuelve un Optional que puede contener el usuario encontrado.
   *
   * @param id ID del usuario a buscar.
   * @return Optional con el usuario si se encuentra, o vacío si no.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("null")
  public Optional<User> findById(@NotNull Long id) {
    return userRepository.findById(id);
  }

  /**
   * 1. Actualiza un usuario existente por su ID.
   * 2. Marca la transacción como de escritura.
   * 3. Suprime las advertencias de nulidad.
   * 4. Devuelve un Optional que puede contener el usuario actualizado.
   * 
   * @param id   ID del usuario a actualizar.
   * @param user Datos del usuario para la actualización.
   * @return Optional con el usuario actualizado si se encuentra, o vacío si no.
   */
  @Override
  @Transactional
  @SuppressWarnings("null")
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

  /**
   * 1. Actualiza un usuario existente por su ID utilizando un DTO.
   * 2. Marca la transacción como de escritura.
   * 3. Suprime las advertencias de nulidad.
   * 4. Devuelve un Optional que puede contener el usuario actualizado.
   * 
   * @param id      ID del usuario a actualizar.
   * @param userDto Datos del usuario para la actualización en forma de DTO.
   * @return Optional con el usuario actualizado si se encuentra, o vacío si no.
   */
  @Override
  @Transactional
  @SuppressWarnings("null")
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

  /**
   * 1. Elimina un usuario por su ID.
   * 2. Marca la transacción como de escritura.
   * 3. Suprime las advertencias de nulidad.
   * 4. Devuelve un Optional que puede contener el usuario eliminado.
   * 
   * @param id ID del usuario a eliminar.
   * @return Optional con el usuario eliminado si se encuentra, o vacío si no.
   */
  @Override
  @Transactional
  @SuppressWarnings("null")
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
