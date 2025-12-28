package com.cryfirock.auth.service;

public interface IUserQueryService {
  // Métodos para validar existencia.
  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  boolean existsByUsername(String username);
}
