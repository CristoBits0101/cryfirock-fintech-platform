package com.cryfirock.auth.service.impl;

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
import com.cryfirock.auth.service.api.IUserService;
import com.cryfirock.auth.util.PasswordUtil;

import jakarta.validation.constraints.NotNull;

/**
 * 1. Se ocupa de las operaciones relacionadas con los usuarios.
 * 2. Implementa la interfaz IUserService.
 * 3. Utiliza JpaUserRepository para interactuar con la base de datos.
 * 4. Proporciona métodos para CRUD de usuarios.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service @Validated
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
     * @param userRepository Repositorio JPA para usuarios.
     * @param rolesHelper Helper para asignar roles a los usuarios.
     * @param userMapper Mapper para convertir entre entidades y DTOs de usuario.
     * @param environment Entorno de configuración de Spring.
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
    @Override @Transactional
    public User save(@NotNull User user) {
        // Asignar rol al usuario recibido.
        user.setRoles(rolesHelper.assignRoles(user));
        // Codificar contraseña del usuario recibido.
        user.setPasswordHash(PasswordUtil.encodeIfRaw(user.getPasswordHash()));
        // Envolvemos el parámetro en Optional para validar nulos sin if explícitos.
        // En findById esta implícito devuelve Optional.of(entidad) si existe.
        return Optional.ofNullable(user)
                // Guardar el usuario en la base de datos.
                .map(userRepository::save)
                // Lanza excepción si el usuario es nulo.
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
    @Override @Transactional(readOnly = true)
    public List<User> findAll() {
        // Retornar todos los usuarios de la base de datos.
        return userRepository
                // Recuperar todos los usuarios de la base de datos.
                .findAll()
                // Convierte el List<User> en un Stream<User>
                .stream()
                // 1. Mira cada usuario del stream.
                // 2. Realiza una acción por cada usuario.
                // 3. No transforma el Stream; no se modifica el tipo de los elementos a retornar.
                // 4. No se modifica la referencia de los elementos a retornar.
                .peek(user ->
                // Establece el puerto del servidor en el usuario.
                user.setPort(
                        // Convierte el puerto a entero.
                        Integer.parseInt(
                                // Obtiene el puerto del archivo de configuración.
                                environment.getProperty("local.server.port"))))
                // Convierte el Stream<User> en un List<User>
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
    @Override @Transactional(readOnly = true) @SuppressWarnings("null")
    public Optional<User> findById(@NotNull Long id) {
        // Busca un usuario por su ID y lo retorna.
        return userRepository.findById(id);
    }

    /**
     * 1. Busca un usuario por su ID con roles cargados.
     * 2. Marca la transacción como de solo lectura.
     * 3. Suprime las advertencias de nulidad.
     * 4. Devuelve un Optional que puede contener el usuario encontrado.
     *
     * @param id ID del usuario a buscar.
     * @return Optional con el usuario si se encuentra, o vacío si no.
     */
    @Override @Transactional(readOnly = true) @SuppressWarnings("null")
    public Optional<User> findByIdWithRoles(@NotNull Long id) {
        // Busca un usuario por su ID con roles cargados y lo retorna.
        return userRepository.findByIdWithRoles(id);
    }

    /**
     * 1. Actualiza un usuario existente por su ID.
     * 2. Marca la transacción como de escritura.
     * 3. Suprime las advertencias de nulidad.
     * 4. Devuelve un Optional que puede contener el usuario actualizado.
     *
     * @param id ID del usuario a actualizar.
     * @param user Datos del usuario para la actualización.
     * @return Optional con el usuario actualizado si se encuentra, o vacío si no.
     */
    @Override @Transactional @SuppressWarnings("null")
    public Optional<User> update(@NotNull Long id, @NotNull User user) {
        // Retorna un Optional con el usuario actualizado si se encuentra o vacío si no.
        return userRepository
                // Busca un usuario por su ID.
                .findById(id)
                // Realiza una operación sobre el Optional devuelto.
                .map(
                        u -> {
                            // Actualiza el nombre del usuario.
                            u.setGivenName(user.getGivenName());
                            // Actualiza el apellido del usuario.
                            u.setFamilyName(user.getFamilyName());
                            // Actualiza la fecha de nacimiento del usuario.
                            u.setDob(user.getDob());
                            // Actualiza el correo electrónico del usuario.
                            u.setEmail(user.getEmail());
                            // Actualiza el número de teléfono del usuario.
                            u.setPhoneNumber(user.getPhoneNumber());
                            // Actualiza la dirección del usuario.
                            u.setAddress(user.getAddress());
                            // Actualiza el nombre de usuario del usuario.
                            u.setUsername(user.getUsername());

                            // Actualiza la contraseña del usuario si se proporciona.
                            if (StringUtils.hasText(user.getPasswordHash()))
                                // Establece la contraseña del usuario.
                                u.setPasswordHash(
                                        // Codifica la contraseña si es raw.
                                        PasswordUtil.encodeIfRaw(
                                                // Obtiene la contraseña del usuario enviado.
                                                user.getPasswordHash()));

                            // Asigna los roles al usuario.
                            u.setRoles(rolesHelper.assignRoles(user));
                            // Establece el estado del usuario.
                            u.setEnabled(user.getEnabled());
                            // Guarda el usuario en la base de datos.
                            return userRepository.save(u);
                        });
    }

    /**
     * 1. Actualiza un usuario existente por su ID utilizando un DTO.
     * 2. Marca la transacción como de escritura.
     * 3. Suprime las advertencias de nulidad.
     * 4. Devuelve un Optional que puede contener el usuario actualizado.
     *
     * @param id ID del usuario a actualizar.
     * @param userDto Datos del usuario para la actualización en forma de DTO.
     * @return Optional con el usuario actualizado si se encuentra, o vacío si no.
     */
    @Override @Transactional @SuppressWarnings("null")
    public Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto userDto) {
        // Retorna un Optional con el usuario actualizado si se encuentra o vacío si no.
        return userRepository
                // Busca un usuario por su ID.
                .findById(id)
                // Realiza una operación sobre el Optional devuelto.
                .map(
                        // Por cada usuario encontrado realiza las siguientes operaciones:
                        u -> {
                            // Actualiza el usuario con los datos del DTO.
                            userMapper.update(u, userDto);

                            // Si el DTO trae contraseña, la codifica y la asigna al usuario.
                            if (userDto.passwordHash() != null && !userDto.passwordHash().isBlank())
                                // Establece la contraseña del usuario.
                                u.setPasswordHash(
                                        // Codifica la contraseña si es raw.
                                        PasswordUtil.encodeIfRaw(
                                                // Obtiene la contraseña del usuario enviado.
                                                userDto.passwordHash()));

                            // Asigna los roles al usuario.
                            u.setRoles(rolesHelper.assignRoles(u));
                            // Guarda el usuario en la base de datos.
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
    @Override @Transactional @SuppressWarnings("null")
    public Optional<User> deleteById(@NotNull Long id) {
        // Retorna un Optional con el usuario eliminado si se encuentra o vacío si no.
        return userRepository
                // Busca un usuario por su ID.
                .findById(id)
                // Realiza una operación sobre el Optional devuelto.
                .map(
                        // Por cada usuario encontrado realiza las siguientes operaciones:
                        user -> {
                            // Elimina el usuario de la base de datos.
                            userRepository.delete(user);
                            // Retorna el usuario eliminado.
                            return user;
                        });
    }
}
