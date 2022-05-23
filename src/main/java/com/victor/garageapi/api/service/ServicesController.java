package com.victor.garageapi.api.service;

import com.victor.garageapi.dto.ResponseMessage;
import com.victor.garageapi.dto.ServiceDto;
import com.victor.garageapi.entity.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/services")
@RequiredArgsConstructor
public class ServicesController {
    private final ServicesService servicesService;

    @PostMapping("new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addService(@RequestBody ServiceDto service) {
        servicesService.addService(service);
        ResponseMessage message = ResponseMessage.builder()
                .message("service added successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Service>> allServices() {
        return new ResponseEntity<>(servicesService.allServices(), HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Service> findServiceById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(servicesService.findServiceById(id), HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Service>> findServicesByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(servicesService.findServicesByName(name), HttpStatus.OK);
    }

    @GetMapping("sku/{sku}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Service> findServiceBySKU(@PathVariable("sku") String SKU) {
        return new ResponseEntity<>(servicesService.findServiceBySKU(SKU), HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteService(@PathVariable("id") Long id) {
        servicesService.deleteService(id);
        ResponseMessage message = ResponseMessage.builder()
                .message(id + " deleted successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete-all-services")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteAllServices() {
        servicesService.deleteAllServices();
        ResponseMessage message = ResponseMessage.builder()
                .message("all services deleted successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }
}
