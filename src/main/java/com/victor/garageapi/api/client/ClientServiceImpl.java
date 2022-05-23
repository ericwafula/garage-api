package com.victor.garageapi.api.client;

import com.victor.garageapi.api.inventory.InventoryItemRepository;
import com.victor.garageapi.api.service.ServiceRepository;
import com.victor.garageapi.dto.ClientDto;
import com.victor.garageapi.entity.Client;
import com.victor.garageapi.entity.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ServiceRepository serviceRepository;

    @Override
    @Transactional
    public void addNewClient(ClientDto clientDto) {
        if (!clientDto.getFirstName().equals("") && !clientDto.getLastName().equals("") && !clientDto.getPhoneNumber().equals("")) {
            Client client = new Client();
            client.setFirstName(clientDto.getFirstName());
            client.setLastName(clientDto.getLastName());
            client.setPhoneNumber(clientDto.getPhoneNumber());
            client.setEmail(clientDto.getEmail());
            client.setGender(clientDto.getGender());
            client.setAge(clientDto.getAge());
            client.setLocation(clientDto.getLocation());
            client.setAddress(clientDto.getAddress());

            clientRepository.save(client);
        }
    }

    @Override
    public List<Client> allAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_ID: " + id));
        return client.get();
    }

    @Override
    public Client findClientByName(String firstName, String lastName) {
        Optional<Client> client = clientRepository.findClientByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_NAME: " + firstName + " " + lastName));
        return client.get();
    }

    @Override
    @Transactional
    public void updateClient(ClientDto clientDto, Long id) {
        Optional<Client> client = clientRepository.findById(id);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_ID: " + id));

        if (!clientDto.getFirstName().equals("")) {
            client.get().setFirstName(clientDto.getFirstName());
        }
        if (!clientDto.getLastName().equals("")) {
            client.get().setLastName(clientDto.getLastName());
        }
        if (!clientDto.getPhoneNumber().equals("")) {
            client.get().setPhoneNumber(clientDto.getPhoneNumber());
        }
        if (!clientDto.getEmail().equals("")) {
            client.get().setEmail(clientDto.getEmail());
        }
        if(clientDto.getAge() != null) {
            client.get().setAge(clientDto.getAge());
        }
        if (!clientDto.getGender().equals("")) {
            client.get().setGender(clientDto.getGender());
        }
        if (!clientDto.getLocation().equals("")) {
            client.get().setLocation(clientDto.getLocation());
        }
        if (!clientDto.getAddress().equals("")) {
            client.get().setAddress(clientDto.getAddress());
        }

        clientRepository.save(client.get());
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_ID: " + id));
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllClients() {
        clientRepository.deleteAll();
    }

    @Override
    public Client findClientByEmail(String email) {
        Optional<Client> client = clientRepository.findClientByEmail(email);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_EMAIL: " + email));
        return client.get();
    }

    @Override
    @Transactional
    public void addProductPurchase(Long clientId, Long productId) {
        Optional<Client> client = clientRepository.findById(clientId);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_ID: " + clientId));
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(productId);
        inventoryItem.orElseThrow(() -> new EntityNotFoundException("NO_INVENTORY_ITEM_FOUND_WITH_ID: " + productId));
        client.get().getInventoryItems().add(inventoryItem.get());
    }

    @Override
    @Transactional
    public void addServicePurchase(Long clientId, Long serviceId) {
        Optional<Client> client = clientRepository.findById(clientId);
        client.orElseThrow(() -> new EntityNotFoundException("NO_CLIENT_FOUND_WITH_ID: " + clientId));
        Optional<com.victor.garageapi.entity.Service> service = serviceRepository.findById(serviceId);
        service.orElseThrow(() -> new EntityNotFoundException("NO_SERVICE_FOUND_WITH_ID: " + serviceId));
        client.get().getServices().add(service.get());
    }
}
