package com.cryfirock.oauth2.provider.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cryfirock.oauth2.provider.model.User;

import jakarta.validation.constraints.NotNull;

@FeignClient(url = "localhost:8082")
public interface UserFeignClient {
    @GetMapping("/{id}")
    Optional<User> findById(@NotNull Long id);
}