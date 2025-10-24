package com.creativadigital360.api.core.service;

public interface IUserQueryService {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

}
