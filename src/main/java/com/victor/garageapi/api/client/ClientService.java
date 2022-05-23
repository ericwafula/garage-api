package com.victor.garageapi.api.client;

import com.victor.garageapi.dto.ClientDto;
import com.victor.garageapi.entity.Client;

import java.util.List;

public interface ClientService {
    void addNewClient(ClientDto clientDto);

    List<Client> allAllClients();

    Client findClientById(Long id);

    Client findClientByName(String firstName, String lastName);

    void updateClient(ClientDto clientDto, Long id);

    void deleteClient(Long id);

    void deleteAllClients();

    Client findClientByEmail(String email);

    void addProductPurchase(Long clientId, Long productId);

    void addServicePurchase(Long clientId, Long serviceId);
}
