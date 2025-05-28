package com.herve.vms.dtos.request.owner;


import com.herve.vms.dtos.request.auth.RegisterUserDTO;
import lombok.Data;

@Data
public class CreateOwnerDTO extends RegisterUserDTO {
    private String address;
}
