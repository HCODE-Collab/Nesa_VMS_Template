package com.herve.vms.services;

import com.herve.vms.models.PlateNumber;

import java.util.UUID;

public interface IPlateNumberService {
    PlateNumber generateAndAssignPlateNumberToOwner(UUID ownerId);
}
