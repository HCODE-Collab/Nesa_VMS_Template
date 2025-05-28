package com.herve.vms.services.impl;

import com.herve.vms.dtos.request.vehicle.CreateVehicleDTO;
import com.herve.vms.dtos.request.vehicle.UpdateVehicleDTO;
import com.herve.vms.dtos.response.vehicle.VehicleResponseDTO;
import com.herve.vms.enums.EPlateStatus;
import com.herve.vms.exceptions.AppException;
import com.herve.vms.exceptions.BadRequestException;
import com.herve.vms.exceptions.NotFoundException;
import com.herve.vms.models.Owner;
import com.herve.vms.models.OwnershipRecord;
import com.herve.vms.models.PlateNumber;
import com.herve.vms.models.Vehicle;
import com.herve.vms.repositories.IOwnerRepository;
import com.herve.vms.repositories.IOwnershipRecordRepository;
import com.herve.vms.repositories.IPlateNumberRepository;
import com.herve.vms.repositories.IVehicleRepository;
import com.herve.vms.services.IVehicleService;
import com.herve.vms.utils.Mapper;
import com.herve.vms.utils.helpers.ChassisNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;
    private final IOwnerRepository ownerRepository;
    private final IPlateNumberRepository plateNumberRepository;
    private final IOwnershipRecordRepository ownershipRecordRepository;
    private final ChassisNumberGenerator chassisNumberGenerator;



    @Override
    @Transactional
    public VehicleResponseDTO createVehicle(CreateVehicleDTO dto) {
        try{

            Owner owner = ownerRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new NotFoundException("Owner not found with ID: " + dto.getOwnerId()));

            PlateNumber plateNumber = plateNumberRepository.findById(dto.getPlateId())
                    .orElseThrow(() -> new NotFoundException("Plate number not found with ID: " + dto.getPlateId()));

            if(!plateNumber.getPlateStatus().equals(EPlateStatus.AVAILABLE)){
                throw new BadRequestException("Plate number is already in use");
            }

            if(!plateNumber.getOwner().getId().equals(owner.getId())){
                throw  new BadRequestException("Plate number does not belong to owner");
            }


            Vehicle vehicle = Mapper.getMapper().map(dto, Vehicle.class);
            String chassisNumber = chassisNumberGenerator.generateUniqueChasisNumber();

            vehicle.setChassisNumber(chassisNumber);
            vehicle.setOwner(owner);
            vehicle.setCurrentPlate(plateNumber);

            vehicle = vehicleRepository.save(vehicle);

            plateNumber.setPlateStatus(EPlateStatus.IN_USE);
            plateNumberRepository.save(plateNumber);


            OwnershipRecord ownershipRecord = OwnershipRecord.builder()
                    .vehicle(vehicle)
                    .owner(owner)
                    .plateNumber(plateNumber)
                    .purchasePrice(dto.getPrice())
                    .transferDate(LocalDateTime.now())
                    .build();

            ownershipRecordRepository.save(ownershipRecord);

            return Mapper.getMapper().map(vehicle, VehicleResponseDTO.class);
        }catch (Exception e){
            throw new AppException("Error creating vehicle: " + e.getMessage());
        }
    }

    @Override
    public VehicleResponseDTO getVehicle(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
        return Mapper.getMapper().map(vehicle, VehicleResponseDTO.class);
    }

    @Override
    public VehicleResponseDTO searchVehicle(String keyword) {
        Vehicle vehicle = vehicleRepository.findVehicleByCurrentPlate_PlateNumber(keyword)
                .or(() -> vehicleRepository.findVehicleByChassisNumber(keyword))
                .or(() -> vehicleRepository.findVehicleByOwner_Profile_NationalId(keyword))
                .orElseThrow(() -> new NotFoundException("Vehicle not found with keyword: " + keyword));
        return Mapper.getMapper().map(vehicle, VehicleResponseDTO.class) ;
    }



    @Override
    public VehicleResponseDTO updateVehicle(UUID vehicleId, UpdateVehicleDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        try{
            Mapper.getMapper().map(dto, vehicle);
            vehicle = vehicleRepository.save(vehicle);
            return Mapper.getMapper().map(vehicle, VehicleResponseDTO.class);
        }catch (Exception e){
            throw new AppException("Error updating vehicle: " + e.getMessage());
        }

    }

    @Override
    public List<Page<VehicleResponseDTO>> getAllVehicles(Pageable pageable) {
        Page<Vehicle> page = vehicleRepository.findAll(pageable);
        return List.of(page.map(vehicle -> Mapper.getMapper().map(vehicle, VehicleResponseDTO.class)));
    }

    @Override
    public Page<VehicleResponseDTO> getAllVehiclesByOwnerId(UUID ownerId, Pageable pageable) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        Page<Vehicle> page = vehicleRepository.findByOwner(owner, pageable);

        return page.map(vehicle -> Mapper.getMapper().map(vehicle, VehicleResponseDTO.class));
    }


    @Override
    public void deleteVehicle(UUID vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
        vehicleRepository.delete(vehicle);

    }


}
