package com.cryfirock.oauth2.provider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-users")
public interface UserFeignClient {
    @GetMapping("/api/validations/exists/email/{email}")
    Boolean checkEmailExists(@PathVariable("email") String email);
}