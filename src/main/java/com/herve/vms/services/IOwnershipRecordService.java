package com.herve.vms.services;

import com.herve.vms.dtos.request.vehicle.TransferVehicleDTO;
import com.herve.vms.dtos.response.vehicle.VehicleOwnershipResponseDTO;

import java.util.List;

public interface IOwnershipRecordService {

    void transferVehicleOwnership(TransferVehicleDTO dto);
    List<VehicleOwnershipResponseDTO> getOwnershipHistoryByChassis(String chassisNumber);
    List<VehicleOwnershipResponseDTO> getOwnershipHistoryByPlate(String plateNumber);


}
