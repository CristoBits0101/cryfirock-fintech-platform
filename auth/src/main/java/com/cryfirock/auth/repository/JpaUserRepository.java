package com.cryfirock.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cryfirock.auth.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> buscarPorEmail(@Param("email") String email);

  @Query(value = "SELECT * FROM users WHERE active = true", nativeQuery = true)
  List<User> findAllActiveUsers();
}
