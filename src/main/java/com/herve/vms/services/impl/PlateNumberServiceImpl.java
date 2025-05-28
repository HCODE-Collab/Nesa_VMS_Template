package com.herve.vms.services.impl;

import com.herve.vms.enums.EPlateStatus;
import com.herve.vms.exceptions.NotFoundException;
import com.herve.vms.models.Owner;
import com.herve.vms.models.PlateNumber;
import com.herve.vms.repositories.IOwnerRepository;
import com.herve.vms.repositories.IPlateNumberRepository;
import com.herve.vms.services.IPlateNumberService;
import com.herve.vms.utils.helpers.PlateNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlateNumberServiceImpl implements IPlateNumberService {

    private final IPlateNumberRepository plateNumberRepository;
    private final IOwnerRepository ownerRepository;
    private final PlateNumberGenerator plateNumberGenerator;

    @Override
    @Transactional
    public PlateNumber generateAndAssignPlateNumberToOwner(UUID ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Owner not found with ID: " + ownerId));

        String plateValue = plateNumberGenerator.generateNextPlateNumber();

        PlateNumber plateNumber = PlateNumber.builder()
                .owner(owner)
                .plateNumber(plateValue)
                .plateStatus(EPlateStatus.AVAILABLE)
                .issuedDate(LocalDateTime.now())
                .build();

        return plateNumberRepository.save(plateNumber);
    }
}
