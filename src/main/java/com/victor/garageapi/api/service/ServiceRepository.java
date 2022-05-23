package com.victor.garageapi.api.service;

import com.victor.garageapi.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findServicesByNameContainsIgnoreCase(String name);

    Optional<Service> findServicesBySKU(String SKU);
}
