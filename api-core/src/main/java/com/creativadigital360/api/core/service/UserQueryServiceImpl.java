package com.creativadigital360.api.core.service;

import com.creativadigital360.api.core.repository.JpaUserRepository;

import org.springframework.stereotype.Service;

/**
 * =================================================================================
 * Paso 9.1: Servicio para comprobar existencia de datos de usuario
 * =================================================================================
 */
@Service
public class UserQueryServiceImpl implements IUserQueryService {

    /**
     * =============================================================================
     * Paso 9.2: Atributos
     * =============================================================================
     */
    // Repositorio de acceso a datos final
    private final JpaUserRepository userRepository;

    /**
     * =============================================================================
     * Paso 9.3: Constructores
     * =============================================================================
     */
    public UserQueryServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * =============================================================================
     * Paso 9.4: Métodos de validación de existencia
     * =============================================================================
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

}
