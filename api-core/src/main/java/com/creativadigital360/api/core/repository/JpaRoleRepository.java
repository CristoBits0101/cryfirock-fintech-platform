package com.creativadigital360.api.core.repository;

import com.creativadigital360.api.core.entity.Role;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
