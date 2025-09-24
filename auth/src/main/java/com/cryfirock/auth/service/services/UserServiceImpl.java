package com.cryfirock.auth.service.services;

import com.cryfirock.auth.service.entities.Role;
import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.exceptions.UserNotFoundException;
import com.cryfirock.auth.service.repositories.RoleRepository;
import com.cryfirock.auth.service.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * ==============================================================
 * Paso 9.1:
 * ==============================================================
 */

public class UserServiceImpl implements IUserService {

    /**
     * ==============================================================
     * Paso 9.2:
     * ==============================================================
     */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ==============================================================
     * Paso 9.3:
     * ==============================================================
     */

    /**
     * Guarda un nuevo usuario en la base de datos
     *
     * @param user objeto User con los datos del nuevo usuario
     * @return usuario guardado
     */
    @Override
    @Transactional
    public User save(User user) {
        // Inicializa una lista para almacenar los roles del usuario
        List<Role> roles = new ArrayList<>();

        // Asigna el rol predeterminado "ROLE_USER"
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

        optionalRoleUser.ifPresent(roles::add);

        // Si el usuario es administrador, agrega "ROLE_ADMIN"
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        // Establece los roles y encripta la contraseña antes de guardar
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guarda y devuelve el usuario
        return userRepository.save(user);
    }

    /**
     * Actualiza un usuario existente por ID
     *
     * @param id   ID del usuario
     * @param user objeto User con los datos actualizados
     * @return Optional con el usuario actualizado si existe, de lo contrario vacío
     */
    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        // Lanza una excepción si el usuario no existe
        if (optionalUser.isEmpty())
            throw new UserNotFoundException("User " + id + " does not exist!");

        // Comprueba si el usuario existe antes de actualizar
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();

            // Actualiza los campos del usuario
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setDob(user.getDob());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setAccountStatus(user.getAccountStatus());

            // Si el usuario no es administrador, asegura que solo tenga el rol "ROLE_USER"
            if (!user.isAdmin()) {
                List<Role> roles = new ArrayList<>();
                Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
                optionalRoleUser.ifPresent(roles::add);
                userToUpdate.setRoles(roles);
            }

            // Guarda y devuelve el usuario actualizado
            return Optional.of(userRepository.save(userToUpdate));
        }
        return optionalUser;
    }

    /**
     * Obtiene todos los usuarios de la base de datos
     *
     * @return lista de todos los usuarios
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Busca un usuario por su ID
     *
     * @param id ID del usuario
     * @return Optional con el usuario si se encuentra, de lo contrario vacío
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Verifica si existe un usuario por correo electrónico
     *
     * @param email correo electrónico del usuario
     * @return true si el correo existe, false en caso contrario
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Verifica si existe un usuario por número telefónico
     *
     * @param phoneNumber número telefónico del usuario
     * @return true si el número existe, false en caso contrario
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    /**
     * Verifica si existe un usuario por nombre de usuario
     *
     * @param username nombre de usuario
     * @return true si el usuario existe, false en caso contrario
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Elimina un usuario de la base de datos
     *
     * @param user objeto User a eliminar
     * @return Optional con el usuario eliminado si existe, de lo contrario vacío
     */
    @Override
    @Transactional
    public Optional<User> deleteUser(User user) {
        // Verifica si el usuario existe antes de eliminar
        // Lanza una excepción si el usuario no existe
        Optional<User> userToDelete = Optional.ofNullable(
                userRepository
                        .findById(user.getId())
                        .orElseThrow(() -> new UserNotFoundException("User " + user.getId() + " does not exist!")));

        // Si el usuario existe, elimínalo
        userToDelete.ifPresent(userRepository::delete);

        return userToDelete;
    }
}
