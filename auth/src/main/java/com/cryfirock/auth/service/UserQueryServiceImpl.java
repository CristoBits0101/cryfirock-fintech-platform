package com.cryfirock.auth.service;

import org.springframework.stereotype.Service;

import com.cryfirock.auth.repository.JpaUserRepository;

@Service
public class UserQueryServiceImpl implements IUserQueryService {
  /**
   * Atributos
   */
  private final JpaUserRepository userRepository;

  public UserQueryServiceImpl(JpaUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Valida si un email ya existe en la base de datos.
   * 
   * @param email email a validar.
   * @return true si el email existe, false en caso contrario.
   */
  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  /**
   * Valida si un teléfono ya existe en la base de datos.
   * 
   * @param phoneNumber teléfono a validar.
   * @return true si el teléfono existe, false en caso contrario.
   */
  @Override
  public boolean existsByPhoneNumber(String phoneNumber) {
    return userRepository.existsByPhoneNumber(phoneNumber);
  }

  /**
   * Valida si un usuario ya existe en la base de datos.
   * 
   * @param username usuario a validar.
   * @return true si el usuario existe, false en caso contrario.
   */
  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
