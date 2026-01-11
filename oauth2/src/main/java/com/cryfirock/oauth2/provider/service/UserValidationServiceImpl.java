package com.cryfirock.oauth2.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryfirock.oauth2.provider.client.UserFeignClient;

@Service
public class UserValidationServiceImpl implements IUserValidationService {
    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public boolean isEmailAlreadyRegistered(String email) {
        try {
            Boolean exists = userFeignClient.checkEmailExists(email);
            return exists != null && exists;
        } catch (Exception e) {
            System.err.println("Error al verificar email: " + e.getMessage());
            return false;
        }
    }
}
