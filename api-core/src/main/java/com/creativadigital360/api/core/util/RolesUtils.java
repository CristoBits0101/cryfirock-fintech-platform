package com.creativadigital360.api.core.util;

import com.creativadigital360.api.core.entity.Role;
import com.creativadigital360.api.core.entity.User;
import com.creativadigital360.api.core.repository.JpaRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
        return (user.isAdmin()
                ? Stream.of(ROLE_USER, ROLE_ADMIN)
                : Stream.of(ROLE_USER))
                .map(role -> roleRepository
                        .findByName(role)
                        .orElseThrow(() -> new IllegalStateException("Missing role " + role)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
