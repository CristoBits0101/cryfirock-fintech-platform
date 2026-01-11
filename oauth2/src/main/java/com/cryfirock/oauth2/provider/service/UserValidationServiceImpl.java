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
        return Boolean.TRUE.equals(userFeignClient.checkEmailExists(email));
    }
}
