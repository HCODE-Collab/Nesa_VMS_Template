package com.herve.vms.dtos.request.auth;


import com.herve.vms.annotations.ValidPassword;
import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private String oldPassword;
    @ValidPassword(message = "Password should be strong")
    private String newPassword;
}