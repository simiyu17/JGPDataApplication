package com.jgp.authentication.domain;

import com.jgp.authentication.dto.RoleDto;
import com.jgp.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

@Getter
@Entity
@Table(name = "user_roles", uniqueConstraints = { @UniqueConstraint(columnNames = { "role_name" }, name = "unq_name") })
public class Role extends BaseEntity {

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    public Role() {
    }

    private Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public static Role createRole(final RoleDto roleDto) {
        return new Role(roleDto.roleName(), roleDto.description());
    }

    public boolean updatePermission(final Permission permission, final boolean isSelected) {
        boolean changed = false;
        if (isSelected) {
            changed = addPermission(permission);
        } else {
            changed = removePermission(permission);
        }

        return changed;
    }

    private boolean addPermission(final Permission permission) {
        return this.permissions.add(permission);
    }

    private boolean removePermission(final Permission permission) {
        return this.permissions.remove(permission);
    }

    public boolean hasPermissionTo(final String permissionCode) {
        boolean match = false;
        for (final Permission permission : this.permissions) {
            if (permission.hasCode(permissionCode)) {
                match = true;
                break;
            }
        }
        return match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), role.getId())
                .append(getRoleName(), role.getRoleName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getRoleName()).toHashCode();
    }
}
