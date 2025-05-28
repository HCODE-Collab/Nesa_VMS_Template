package com.herve.vms.dtos.request.user;


import com.herve.vms.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private User user;
}