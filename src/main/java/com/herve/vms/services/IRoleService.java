package com.herve.vms.services;


import com.herve.vms.dtos.request.role.CreateRoleDTO;
import com.herve.vms.dtos.response.role.RoleResponseDTO;
import com.herve.vms.dtos.response.role.RolesResponseDTO;
import com.herve.vms.enums.ERole;
import com.herve.vms.models.Role;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IRoleService {
    public Role getRoleById(UUID roleId);

    public Role getRoleByName(ERole roleName);

    public void createRole(ERole roleName);

    public RoleResponseDTO createRole(CreateRoleDTO createRoleDTO);

    public RolesResponseDTO getRoles(Pageable pageable);

    public void deleteRole(UUID roleId);

    public boolean isRolePresent(ERole roleName);
}