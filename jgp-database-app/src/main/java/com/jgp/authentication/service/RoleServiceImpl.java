package com.jgp.authentication.service;

import com.jgp.authentication.domain.Permission;
import com.jgp.authentication.domain.PermissionRepository;
import com.jgp.authentication.domain.Role;
import com.jgp.authentication.domain.RoleRepository;
import com.jgp.authentication.dto.RoleDto;
import com.jgp.authentication.exception.RoleNotFoundException;
import com.jgp.authentication.mapper.RoleMapper;
import com.jgp.infrastructure.core.domain.JdbcSupport;
import com.jgp.shared.exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Role createRole(RoleDto roleDto) {
        return this.roleRepository.save(Role.createRole(roleDto));
    }

    @Override
    @Transactional
    public void updateRole(Long roleId, RoleDto roleDto) {
        var role = this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFound(HttpStatus.NOT_FOUND));
        role.updateRole(roleDto);
        this.roleRepository.save(role);
    }

    @Override
    @Transactional
    public void updateRolePermissions(Long roleId, List<String> permissionCodes) {
        var role = this.roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
        final Collection<Permission> selectedPermissions = this.permissionRepository.findAllByCodes(permissionCodes);
        role.updatePermissions(new HashSet<>(selectedPermissions));
        this.roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.roleRepository.deleteById(roleId);
    }

    @Override
    public Collection<RoleDto> retrieveAllRoles() {
        return this.roleMapper.toDtoList(this.roleRepository.findAll());
    }

    @Override
    public RoleDto retrieveOne(Long roleId) {
        return this.roleMapper.toDto(this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFound(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Collection<RoleDto> retrieveAppUserRoles(Long appUserId) {
        final var roleRowMapper = new RoleRowMapper();
        final String sql = "select " + RoleRowMapper.ROLES_SCHEMA + " inner join appuser_role"
                + " ar on ar.role_id = r.id where ar.appuser_id= ?";

        return this.jdbcTemplate.query(sql, roleRowMapper, appUserId);
    }

    protected static final class RoleRowMapper implements RowMapper<RoleDto> {

        private static final String ROLES_SCHEMA = " r.id as id, r.role_name as name, r.description as description from m_role r";

        @Override
        public RoleDto mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

            final Long id = JdbcSupport.getLong(rs, "id");
            final String name = rs.getString("name");
            final String description = rs.getString("description");

            return new RoleDto(id, name, description);
        }
    }
}
