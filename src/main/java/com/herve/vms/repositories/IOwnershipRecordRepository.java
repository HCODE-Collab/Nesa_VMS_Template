package com.herve.vms.repositories;

import com.herve.vms.models.OwnershipRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface IOwnershipRecordRepository extends JpaRepository<OwnershipRecord, UUID> {
    List<OwnershipRecord> findOwnershipRecordsByVehicle_ChassisNumber(String vehicleChassisNumber);

    List<OwnershipRecord> findOwnershipRecordsByPlateNumber_PlateNumber(String plateNumberPlateNumber);
}
