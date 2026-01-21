package com.cryfirock.auth.service.impl;

import org.springframework.stereotype.Service;

import com.cryfirock.auth.repository.JpaUserRepository;
import com.cryfirock.auth.service.api.IUserQueryService;

/**
 * 1. Se ocupa de las consultas relacionadas con los usuarios.
 * 2. Implementa la interfaz IUserQueryService.
 * 3. Utiliza JpaUserRepository para interactuar con la base de datos.
 * 4. Proporciona métodos para verificar la existencia de datos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service
public class UserQueryServiceImpl implements IUserQueryService {
    // Repositorio JPA para usuarios.
    private final JpaUserRepository userRepository;

    // Constructor que inyecta el repositorio de usuarios.
    public UserQueryServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Verifica si un correo electrónico ya está registrado.
    // 2. Verifica si un número de teléfono ya está registrado.
    // 3. Verifica si un nombre de usuario ya está registrado.
    // 4. Utiliza métodos del repositorio para realizar las verificaciones.
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
}
