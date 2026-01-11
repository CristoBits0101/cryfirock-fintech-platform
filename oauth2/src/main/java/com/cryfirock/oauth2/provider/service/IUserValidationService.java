package com.cryfirock.oauth2.provider.service;

public interface IUserValidationService {
    boolean isEmailAlreadyRegistered(String email);
}
