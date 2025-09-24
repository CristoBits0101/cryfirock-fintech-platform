package com.cryfirock.auth.service.services;

/**
 * Dependencies
 */

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

import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.models.AccountStatus;
import com.cryfirock.auth.service.repositories.UserRepository;

// Implements UserDetailsService to authenticate users using JPA
// It runs every time Spring Security needs to authenticate a user to the system
// When a user attempts to log in through the authentication filter
// When accessing a protected resource and Spring Security needs to verify the user's identity
@Service
public class JpaUserDetailsService implements UserDetailsService {

    /**
     * Attributes
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their username
     * 
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look for the user in the database
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // If the user is not found, throw an exception
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(String.format("User with username %s does not exist", username));

        // Extract the user from Optional
        User user = optionalUser.orElseThrow();

        // Map user roles to Spring Security's GrantedAuthority objects
        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Determine if the user account is enabled based on their account status
        boolean isEnabled = user.getAccountStatus() == AccountStatus.ACTIVE;

        // Return a Spring Security UserDetails object with the user's information
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
