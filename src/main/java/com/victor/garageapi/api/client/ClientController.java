package com.victor.garageapi.api.client;

import com.victor.garageapi.dto.ClientDto;
import com.victor.garageapi.dto.ResponseMessage;
import com.victor.garageapi.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addNewClient(@RequestBody ClientDto clientDto) {
        clientService.addNewClient(clientDto);
        ResponseMessage message = ResponseMessage.builder()
                .message(clientDto.getFirstName() + "added successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Client>> allClients() {
        return new ResponseEntity<>(clientService.allAllClients(), HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> findClientById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.findClientById(id), HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> findClientByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(clientService.findClientByEmail(email), HttpStatus.OK);
    }

    @GetMapping("name/{firstName}&{lastName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> findClientByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(clientService.findClientByName(firstName, lastName), HttpStatus.OK);
    }

    @PutMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> updateClient(@RequestBody ClientDto clientDto, @PathVariable("id") Long id) {
        clientService.updateClient(clientDto, id);
        ResponseMessage message = ResponseMessage.builder()
                .message(id + " updated successfully")
                .build();

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PutMapping("clientId/{clientId}/productInventoryId/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addProductPurchase(@PathVariable("clientId") Long clientId, @PathVariable("productId") Long productId) {
        clientService.addProductPurchase(clientId, productId);
        ResponseMessage message = ResponseMessage.builder()
                .message("client's: " + clientId + " order added successfully. Product id = " + productId)
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PutMapping("clientId/{clientId}/serviceId/{serviceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> addServicePurchase(@PathVariable("clientId") Long clientId, @PathVariable("serviceId") Long serviceId) {
        clientService.addServicePurchase(clientId, serviceId);
        ResponseMessage message = ResponseMessage.builder()
                .message("client's " + clientId + " order added successfully. Service id = " + serviceId)
                .build();

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        ResponseMessage message = ResponseMessage.builder()
                .message(id + " deleted successfully")
                .build();
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete-all-clients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteAllClients() {
        clientService.deleteAllClients();
        ResponseMessage message = ResponseMessage.builder()
                .message("all clients deleted successfully")
                .build();

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }
}
