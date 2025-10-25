package com.cryfirock.auth.repository;

import com.cryfirock.auth.entity.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface JpaRoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
