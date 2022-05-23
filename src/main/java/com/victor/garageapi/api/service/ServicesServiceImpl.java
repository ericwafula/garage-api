package com.victor.garageapi.api.service;

import com.victor.garageapi.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository serviceRepository;

    @Override
    @Transactional
    public void addService(ServiceDto serviceDto) {
        if (!serviceDto.getName().equals("") && !serviceDto.getSKU().equals("") && serviceDto.getPrice() != null && serviceDto.getIsAvailable() != null) {
            com.victor.garageapi.entity.Service service = new com.victor.garageapi.entity.Service();
            service.setName(serviceDto.getName());
            service.setPrice(service.getPrice());
            service.setSKU(service.getSKU());
            service.setAvailable(serviceDto.getIsAvailable());
            service.setDateOfPurchase(LocalDateTime.now());

            serviceRepository.save(service);
        }
    }

    @Override
    public List<com.victor.garageapi.entity.Service> allServices() {
        return serviceRepository.findAll();
    }

    @Override
    public com.victor.garageapi.entity.Service findServiceById(Long id) {
        Optional<com.victor.garageapi.entity.Service> service = serviceRepository.findById(id);
        service.orElseThrow(() -> new EntityNotFoundException("NO_SERVICE_FOUND_WITH_ID: " + id));
        return service.get();
    }

    @Override
    public List<com.victor.garageapi.entity.Service> findServicesByName(String name) {
        return serviceRepository.findServicesByNameContainsIgnoreCase(name);
    }

    @Override
    public com.victor.garageapi.entity.Service findServiceBySKU(String sku) {
        Optional<com.victor.garageapi.entity.Service> service = serviceRepository.findServicesBySKU(sku);
        service.orElseThrow(() -> new EntityNotFoundException("NO_SERVICE_FOUND_WITH_SKU: " + sku));
        return service.get();
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        Optional<com.victor.garageapi.entity.Service> service = serviceRepository.findById(id);
        service.orElseThrow(() -> new EntityNotFoundException("NO_SERVICE_FOUND_WITH_ID: " + id));
        serviceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllServices() {
        serviceRepository.deleteAll();
    }
}
