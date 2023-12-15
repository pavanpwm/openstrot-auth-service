package com.openstrot.auth.realm.client.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openstrot.auth.realm.client.model.Client;
import com.openstrot.auth.realm.client.service.ClientService;

@RestController
@RequestMapping("/api/realms/{realmId}/clients")
public class ClientController {

	@Autowired private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@PathVariable String realmId, @RequestBody Client client) {
        Client createdClient = clientService.createClient(realmId, client);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Client>> getAllClients(@PathVariable String realmId,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                     @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Client> clientsPage = clientService.getAllClientsInRealm(realmId, pageable);
        return new ResponseEntity<>(clientsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String realmId, @PathVariable String id) {
        Optional<Client> client = clientService.getClientInRealmById(realmId, id);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String realmId, @PathVariable String id, @RequestBody Client updatedClient) {
        Optional<Client> client = clientService.updateClientInRealm(realmId, id, updatedClient);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable String realmId, @PathVariable String id) {
        clientService.deleteClientInRealm(realmId, id);
    }

    private Sort parseSort(String[] sort) {
        if (sort.length >= 2) {
            String property = sort[0];
            String direction = sort[1];
            return Sort.by(Sort.Order.by(property).with(Sort.Direction.fromString(direction)));
        } else {
            return Sort.unsorted();
        }
    }
}
