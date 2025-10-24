package com.creativadigital360.api.core.repository;

import com.creativadigital360.api.core.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.enabled = com.creativadigital360.api.core.model.AccountStatus.ACTIVE")
    List<User> findAllActiveUsers();

}
