package com.victor.garageapi.api.client;

import com.victor.garageapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    Optional<Client> findClientByEmail(String email);
}
