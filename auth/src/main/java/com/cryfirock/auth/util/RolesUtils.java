package com.cryfirock.auth.util;

import com.cryfirock.auth.entity.Role;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.repository.JpaRoleRepository;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class RolesUtils {
  private static final String ROLE_USER = "ROLE_USER";
  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private final JpaRoleRepository roleRepository;

  public RolesUtils(JpaRoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public List<Role> assignRoles(User user) {
    return (user.isAdmin() ? Stream.of(ROLE_USER, ROLE_ADMIN) : Stream.of(ROLE_USER))
        .map(
            role ->
                roleRepository
                    .findByName(role)
                    .orElseThrow(() -> new IllegalStateException("Missing role " + role)))
        .toList();
  }
}
