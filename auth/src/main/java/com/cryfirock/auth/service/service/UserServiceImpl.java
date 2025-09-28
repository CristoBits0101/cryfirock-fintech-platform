package com.cryfirock.auth.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.cryfirock.auth.service.dto.UserUpdateDto;
import com.cryfirock.auth.service.entity.User;
import com.cryfirock.auth.service.exception.UserNotFoundException;
import com.cryfirock.auth.service.mapper.UserMapper;
import com.cryfirock.auth.service.repository.UserRepository;
import com.cryfirock.auth.service.util.PasswordUtils;
import com.cryfirock.auth.service.util.RolesUtils;

import jakarta.validation.constraints.NotNull;

/**
 * =================================================================================================================
 * Paso 9.1: Servicio de dominio de usuarios
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
    // Referencia final no cambia y bean scope singleton misma instancia en petición
    private final UserRepository userRepository;

    // Referencia final no cambia y bean scope singleton misma instancia en petición
    private final RolesUtils rolesUtils;
    private final PasswordUtils passwordUtils;

    //
    private final UserMapper userMapper;

    /**
     * =============================================================================================================
     * Paso 9.3: Constructores
     * =============================================================================================================
     */

    // El contenedor ApplicationContext crea la instancia y la registra como bean
    // Si hay un solo constructor @Autowired es opcional
    public UserServiceImpl(
            // Persistencia de usuarios
            UserRepository userRepository,
            // Resolución de roles
            RolesUtils rolesUtils,
            // Codificación y verificación de contraseñas
            PasswordUtils passwordUtils,
            //
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

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Rollback deshace los cambios ante cualquier Exception checked y unchecked
    @Transactional
    // Guarda y devuelve el usuario con referencia no nula en el parámetro
    public User save(@NotNull User user) {
        // Asigna los roles al usuario
        user.setRoles(rolesUtils.assignRoles(user));
        // Hashea la contraseña del usuario BCrypt si aún no lo está
        user.setPasswordHash(passwordUtils.encodeIfRaw(user.getPasswordHash()));
        // Almacena y retorna el usuario
        return Optional.ofNullable(user)
                .map(userRepository::save)
                // El error puede manejarse con @RestControllerAdvice o con @ResponseStatus
                .orElseThrow(() -> new IllegalArgumentException("User must not be null"));
    }

    /**
     * =============================================================================================================
     * Paso 9.5: Métodos read
     * =============================================================================================================
     */

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Rollback: Deshace los cambios ante cualquier Exception checked y unchecked
    // readOnly = true: Marca la transacción como lectura sin permisos de escritura
    @Transactional(readOnly = true)
    // Devuelve una lista con todos los usuarios
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Rollback: Deshace los cambios ante cualquier Exception checked y unchecked
    // readOnly = true: Marca la transacción como lectura sin permisos de escritura
    @Transactional(readOnly = true)
    // Busca y devuelve un usuario por su identificador
    public Optional<User> findById(@NotNull Long id) {
        return userRepository.findById(id);
    }

    /**
     * =============================================================================================================
     * Paso 9.6: Métodos update
     * =============================================================================================================
     */

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Rollback deshace los cambios ante cualquier Exception checked y unchecked
    @Transactional
    // Actualiza el usuario por id requiere id y usuario no nulos
    public Optional<User> update(@NotNull Long id, @NotNull User user) {
        // Retornamos un opcional de usuario
        return Optional.of(
                // Llamamos al repositorio de usuarios
                userRepository
                        // Buscamos por ID recibido al usuario a actualizar
                        .findById(id)
                        // Si existe se ejecuta el map que recibe al usuario
                        .map(u -> {
                            // Identidad
                            u.setGivenName(user.getGivenName());
                            u.setFamilyName(user.getFamilyName());
                            u.setDob(user.getDob());

                            // Contacto
                            u.setEmail(user.getEmail());
                            u.setPhoneNumber(user.getPhoneNumber());
                            u.setAddress(user.getAddress());

                            // Cuenta
                            u.setUsername(user.getUsername());

                            if (user.getPasswordHash() != null && !user.getPasswordHash().isBlank()) {
                                u.setPasswordHash(passwordUtils.encodeIfRaw(user.getPasswordHash()));
                            }

                            // Acceso
                            u.setRoles(rolesUtils.assignRoles(user));
                            u.setEnabled(user.isEnabled());

                            return userRepository.save(u);
                        })
                        .orElseThrow(() -> new UserNotFoundException("User " + id + " does not exist!")));
    }

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Rollback deshace los cambios ante cualquier Exception checked y unchecked
    @Transactional
    // Actualiza el usuario por id requiere id y usuario no nulos
    public Optional<User> update(@NotNull Long id, @NotNull UserUpdateDto userDto) {
        // Retornamos un opcional de usuario
        return Optional.of(
                // Llamamos al repositorio de usuarios
                userRepository
                        // Buscamos por ID recibido al usuario a actualizar
                        .findById(id)
                        // Si existe se ejecuta el map que recibe al usuario
                        .map(user -> {
                            // Aplica solo campos no nulos del DTO
                            userMapper.update(user, userDto);

                            // Si llega password y no está en blanco la hashea
                            if (userDto.passwordHash() != null && !userDto.passwordHash().isBlank())
                                user.setPasswordHash(
                                        passwordUtils.encodeIfRaw(
                                                userDto.passwordHash()));

                            // Asignamos los roles correspondientes
                            user.setRoles(rolesUtils.assignRoles(userDto));

                            // Retornamos el usuario actualizado
                            return userRepository.save(user);
                        })
                        .orElseThrow(() -> new UserNotFoundException("User " + id + " does not exist!")));
    }

    /**
     * =============================================================================================================
     * Paso 9.7: Métodos delete
     * =============================================================================================================
     */

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User " + id + " does not exist!");
        }
        userRepository.deleteById(id);
    }

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    @Transactional
    public Optional<User> deleteUser(@NotNull User user) {
        return Optional.of(
                userRepository
                        .findById(user.getId())
                        .map(u -> {
                            userRepository.delete(u);
                            return u;
                        })
                        .orElseThrow(() -> new UserNotFoundException("User " + user.getId() + " does not exist!")));
    }

    /**
     * =============================================================================================================
     * Paso 9.8: Métodos de validación de existencia
     * =============================================================================================================
     */

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Devuelve true si existe un email
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Devuelve true si existe un phoneNumber
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    // Implementa y sobrescribe el método de la interfaz con nueva lógica de negocio
    @Override
    // Devuelve true si existe un usuario
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
