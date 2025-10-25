package com.creativadigital360.auth.service;

import com.creativadigital360.auth.entity.User;
import com.creativadigital360.auth.model.AccountStatus;
import com.creativadigital360.auth.repository.JpaUserRepository;

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

/**
 * =====================================================================================================================
 * Paso 16.1: Sirve para verificar la identidad del usuario cuando intenta login
 * =====================================================================================================================
 */
@Service
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
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(String.format("User with username %s does not exist", username));

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
