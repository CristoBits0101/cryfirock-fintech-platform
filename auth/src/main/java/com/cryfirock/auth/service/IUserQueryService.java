package com.cryfirock.auth.service;

public interface IUserQueryService {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);
}
