package com.victor.garageapi.api.service;

import com.victor.garageapi.dto.ServiceDto;
import com.victor.garageapi.entity.Service;

import java.util.List;

public interface ServicesService {
    void addService(ServiceDto service);

    List<Service> allServices();

    Service findServiceById(Long id);

    List<Service> findServicesByName(String name);

    Service findServiceBySKU(String sku);

    void deleteService(Long id);

    void deleteAllServices();
}
