package com.cryfirock.auth.service.impl;

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

import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.repository.JpaUserRepository;
import com.cryfirock.auth.type.AccountStatus;

/**
 * 1. Implementación del servicio de detalles de usuario para Spring Security.
 * 2. Implementa UserDetailsService para la autenticación de usuarios.
 * 3. Utiliza JpaUserRepository para consultar usuarios en la base de datos.
 * 4. Convierte entidades User en objetos UserDetails de Spring Security.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service
public class JpaUserDetailsServiceImpl implements UserDetailsService {
    // 1. Repositorio JPA para usuarios.
    // 2. Inyectado automáticamente por Spring.
    @Autowired
    private JpaUserRepository userRepository;

    /**
     * 1. Carga un usuario por su nombre de usuario.
     * 2. Marca la transacción como de solo lectura.
     * 3. Lanza UsernameNotFoundException si el usuario no existe.
     * 4. Convierte los roles del usuario en GrantedAuthority.
     * 5. Retorna un UserDetails con los datos del usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return UserDetails con la información del usuario.
     * @throws UsernameNotFoundException Si el usuario no existe.
     */
    @Override @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(
                    String.format("User with username %s does not exist", username));

        User user = optionalUser.orElseThrow();

        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                user.getEnabled() == AccountStatus.ACTIVE,
                true,
                true,
                true,
                authorities);
    }
}
