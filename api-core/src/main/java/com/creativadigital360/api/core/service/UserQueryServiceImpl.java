package com.creativadigital360.api.core.service;

import com.creativadigital360.api.core.repository.JpaUserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements IUserQueryService {

    private final JpaUserRepository userRepository;

    public UserQueryServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
