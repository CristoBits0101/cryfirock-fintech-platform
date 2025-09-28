package com.cryfirock.auth.service.service;

import com.cryfirock.auth.service.repository.UserRepository;

/**
 * =================================================================================
 * Paso 9.1: Servicio de dominio de usuarios
 * =================================================================================
 */

public class UserQueryServiceImpl implements IUserQueryService {

    /**
     * =============================================================================
     * Paso 9.2: Atributos
     * =============================================================================
     */

    // Repositorios de acceso a datos final
    // Referencia final no cambia y bean scope singleton misma instancia en petición
    private final UserRepository userRepository;

    /**
     * =============================================================================
     * Paso 9.3: Constructores
     * =============================================================================
     */

    // El contenedor ApplicationContext crea la instancia y la registra como bean
    // Si hay un solo constructor @Autowired es opcional
    // Persistencia de usuarios
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * =============================================================================
     * Paso 9.4: Métodos de validación de existencia
     * =============================================================================
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
