package com.cryfirock.oauth2.provider.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryfirock.oauth2.provider.client.UserFeignClient;
import com.cryfirock.oauth2.provider.service.impl.IUserValidationService;

/**
 * 1. Implementación del servicio de validación de usuarios.
 * 2. Utiliza Feign Client para comunicarse con el microservicio de usuarios.
 * 3. Proporciona validaciones de email y otros datos de usuario.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-15
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service
public class UserValidationServiceImpl implements IUserValidationService {
    /**
     * Cliente Feign para comunicación con el microservicio de usuarios.
     */
    @Autowired
    private final UserFeignClient userFeignClient;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param userFeignClient Cliente Feign para el microservicio de usuarios.
     */
    public UserValidationServiceImpl(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmailAlreadyRegistered(String email) {
        return Boolean.TRUE.equals(userFeignClient.checkDataExists(email, null, null).getBody());
    }
}
