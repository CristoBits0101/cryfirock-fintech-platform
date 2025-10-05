package com.cryfirock.auth.service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.auth.service.entity.User;
import com.cryfirock.auth.service.model.AccountStatus;
import com.cryfirock.auth.service.repository.JpaUserRepository;

/**
 * =====================================================================================================================
 * Paso 16.1: Sirve para verificar la identidad del usuario cuando intenta login
 * =====================================================================================================================
 */

// Estereotipo que registra el bean en el contenedor y marca lógica de negocio
@Service
// Se ejecuta cuando se accede a un recurso protegido o al iniciar sesión
// Cuando se
public class JpaUserDetailsServiceImpl implements UserDetailsService {

    /**
     * =================================================================================================================
     * Paso 16.2: Inyección del repositorio de usuarios para verificar existencia
     * =================================================================================================================
     */

    @Autowired
    private JpaUserRepository userRepository;

    /**
     * Carga un usuario por su nombre de usuario
     *
     * @param username nombre de usuario
     * @return instancia de UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    // Rollback: Deshace los cambios ante cualquier Exception checked y unchecked
    // readOnly = true: Marca la transacción como lectura sin permisos de escritura
    @Transactional(readOnly = true)
    // Busca el usuario en la base de datos mediante su nombre de usuario
    // El usuario podría ser un email o un username en la petición de login
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Busca el usuario en la base de datos
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // 2. Si no existe se lanza una excepción perteneciente a Spring Security
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(String.format("User with username %s does not exist", username));

        // 3. Si existe se obtiene los datos del usuario
        User user = optionalUser.orElseThrow();

        // 4. Los roles del usuario se convierten en GrantedAuthority para Spring
        List<GrantedAuthority> authorities = user
                // Se obtienen los roles del usuario
                .getRoles()
                // Se convierten a un Stream para procesarlos
                .stream()
                // Se mapea cada rol a un SimpleGrantedAuthority
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                // Se colectan los resultados en una lista de GrantedAuthority
                .collect(Collectors.toList());

        // Devuelve una instancia de UserDetails con la información del usuario
        // User es una implementación de UserDetails que proporciona Spring Security
        return new org.springframework.security.core.userdetails.User(
                // Nombre de usuario
                user.getUsername(),
                // Contraseña del usuario encriptada
                user.getPasswordHash(),
                // Si el usuario está habilitado o no
                user.getEnabled() == AccountStatus.ACTIVE,
                // Si la cuenta no ha expirado
                true,
                // Si las credenciales no han expirado
                true,
                // Si la cuenta no está bloqueada
                true,
                // Los roles del usuarioconvertidos a GrantedAuthority
                authorities);
    }

}
