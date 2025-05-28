package com.herve.vms.mapper;

import com.herve.vms.dtos.response.owner.OwnerResponseDTO;
import com.herve.vms.models.Owner;
import com.herve.vms.models.User;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {


    public static OwnerResponseDTO mapToOwnerResponseDTO(User user , Owner owner){

        return OwnerResponseDTO.builder()
                .id(owner.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .nationalId(user.getNationalId())
                .Address(owner.getAddress())
                .build();
    }


}
