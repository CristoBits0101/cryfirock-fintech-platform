package com.cryfirock.oauth2.provider.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "localhost:8082")
public interface UserFeignClient {
}