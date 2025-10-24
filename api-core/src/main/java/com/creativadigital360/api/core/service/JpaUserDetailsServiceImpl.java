package com.creativadigital360.api.core.service;

import com.creativadigital360.api.core.entity.User;
import com.creativadigital360.api.core.model.AccountStatus;
import com.creativadigital360.api.core.repository.JpaUserRepository;

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

@Service
public class JpaUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JpaUserRepository userRepository;

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
