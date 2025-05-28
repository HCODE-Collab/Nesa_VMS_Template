package com.herve.vms.services;



import com.herve.vms.dtos.request.auth.UpdateUserDTO;
import com.herve.vms.dtos.request.user.CreateAdminDTO;
import com.herve.vms.dtos.request.user.UserResponseDTO;
import com.herve.vms.dtos.request.user.UserRoleModificationDTO;
import com.herve.vms.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User findUserById(UUID userId);

    User getLoggedInUser();

    UserResponseDTO createAdmin(CreateAdminDTO createUserDTO);

    List<User> getUsers();

    UserResponseDTO getUserById(UUID uuid);

    UserResponseDTO updateUser(UUID userId, UpdateUserDTO updateUserDTO);

    UserResponseDTO addRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    UserResponseDTO removeRoles(UUID userId, UserRoleModificationDTO userRoleModificationDTO);

    void deleteUser(UUID userId);
}
