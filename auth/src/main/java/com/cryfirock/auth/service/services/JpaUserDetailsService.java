package com.cryfirock.auth.service.services;

import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.models.AccountStatus;
import com.cryfirock.auth.service.repositories.UserRepository;
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

// Implementa UserDetailsService para autenticar usuarios usando JPA
// Se ejecuta cada vez que Spring Security necesita autenticar un usuario en el sistema
// Cuando un usuario intenta iniciar sesión mediante el filtro de autenticación
// Cuando accede a un recurso protegido y Spring Security debe verificar la identidad del usuario
@Service
public class JpaUserDetailsService implements UserDetailsService {

    /**
     * Atributos
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Carga un usuario por su nombre de usuario
     *
     * @param username nombre de usuario
     * @return instancia de UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Si el usuario no se encuentra, lanza una excepción
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(String.format("User with username %s does not exist", username));

        // Extrae el usuario del Optional
        User user = optionalUser.orElseThrow();

        // Convierte los roles del usuario en objetos GrantedAuthority de Spring Security
        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Determina si la cuenta del usuario está habilitada según su estado
        boolean isEnabled = user.getAccountStatus() == AccountStatus.ACTIVE;

        // Devuelve un objeto UserDetails de Spring Security con la información del usuario
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                isEnabled,
                true,
                true,
                true,
                authorities);
    }

}
