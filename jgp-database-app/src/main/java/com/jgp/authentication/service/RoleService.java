package com.jgp.authentication.service;

import com.jgp.authentication.domain.Role;
import com.jgp.authentication.dto.RoleDto;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    Role createRole(RoleDto roleDto);

    void updateRole(Long roleId, RoleDto roleDto);

    void updateRolePermissions(Long roleId, List<String> permissionCodes);

    void deleteRole(Long roleId);

    Collection<RoleDto> retrieveAllRoles();

    RoleDto retrieveOne(Long roleId);

    Collection<RoleDto> retrieveAppUserRoles(Long appUserId);
}
