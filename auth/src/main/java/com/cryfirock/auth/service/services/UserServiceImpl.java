package com.cryfirock.auth.service.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.cryfirock.auth.service.entities.Role;
import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.exceptions.UserNotFoundException;
import com.cryfirock.auth.service.repositories.RoleRepository;
import com.cryfirock.auth.service.repositories.UserRepository;

import jakarta.validation.constraints.NotNull;

/**
 * ==============================================================================================
 * Paso 9.1:
 * ==============================================================================================
 */

// Tipo de componente
@Service
@Validated
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    /**
     * ==========================================================================================
     * Paso 9.2: Atributos
     * ==========================================================================================
     */

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * ==========================================================================================
     * Paso 9.3: Constructores
     * ==========================================================================================
     */

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ==========================================================================================
     * Paso 9.4: Métodos create
     * ==========================================================================================
     */

    //
    @Override
    @Transactional
    public User save(@NotNull User user) {
        user.setRoles(resolveRoles(user));
        user.setPasswordHash(encodeIfRaw(user.getPasswordHash()));
        return userRepository.save(user);
    }

    /**
     * ==========================================================================================
     * Paso 9.5: Métodos read
     * ==========================================================================================
     */

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * ==========================================================================================
     * Paso 9.6: Métodos update
     * ==========================================================================================
     */

    @Override
    @Transactional
    public Optional<User> update(@NotNull Long id, @NotNull User user) {
        User u = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User " + id + " does not exist!"));

        u.setGivenName(user.getGivenName());
        u.setFamilyName(user.getFamilyName());
        u.setEmail(user.getEmail());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setUsername(user.getUsername());
        u.setDob(user.getDob());
        u.setAddress(user.getAddress());
        u.setEnabled(user.isEnabled());

        if (user.getPasswordHash() != null && !user.getPasswordHash().isBlank()) {
            u.setPasswordHash(encodeIfRaw(user.getPasswordHash()));
        }

        // Una sola asignación de roles (añade ROLE_ADMIN si isAdmin() == true)
        u.setRoles(resolveRoles(user));

        return Optional.of(userRepository.save(u));
    }

    /**
     * ==========================================================================================
     * Paso 9.7: Métodos delete
     * ==========================================================================================
     */

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User " + id + " does not exist!");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(@NotNull User user) {
        User u = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User " + user.getId() + " does not exist!"));
        userRepository.delete(u);
        return Optional.of(u);
    }

    /**
     * ==========================================================================================
     * Paso 9.8: Métodos de validación de existencia
     * ==========================================================================================
     */

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * ==========================================================================================
     * Paso 9.9: Helpers
     * ==========================================================================================
     */

    private List<Role> resolveRoles(User user) {
        List<Role> roles = new ArrayList<>();

        Role basic = roleRepository
                .findByName(ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("Missing role " + ROLE_USER));
        roles.add(basic);

        if (user.isAdmin()) {
            roleRepository.findByName(ROLE_ADMIN).ifPresent(roles::add);
        }
        return roles;
    }

    private static Predicate<String> startsWithAny(String... p) {
        return s -> s != null && Arrays.stream(p).anyMatch(s::startsWith);
    }

    private static final Predicate<String> IS_BCRYPT = startsWithAny("$2a$", "$2b$", "$2y$");

    private String encodeIfRaw(String rawOrHash) {
        if (rawOrHash == null) return null;
        boolean looksHashed = IS_BCRYPT.test(rawOrHash);
        return looksHashed ? rawOrHash : passwordEncoder.encode(rawOrHash);
    }

}
