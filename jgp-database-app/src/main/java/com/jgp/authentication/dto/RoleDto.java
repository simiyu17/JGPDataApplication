package com.jgp.authentication.dto;

import java.util.Set;

public record RoleDto(
        Long id,
        String roleName,
        String description,
        Set<String> permissions
) {
}
