package com.cryfirock.auth.repository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.cryfirock.auth.entity.Role;
public interface JpaRoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
