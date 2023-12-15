package com.openstrot.auth.realm.client.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.client.model.Client;
import com.openstrot.auth.realm.client.repository.ClientRepository;
import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;
import com.openstrot.auth.realm.service.AuditService;

@Service
public class ClientService {

	@Autowired private ClientRepository clientRepository;
	@Autowired private RealmRepository realmRepository;
	@Autowired private AuditService auditService;

    // Create
    public Client createClient(String realmId, Client client) {
        // Assuming client has a realmId field
        // Validate that the provided realmId exists
        Realm realm = realmRepository.findById(realmId).orElse(null);
        if (realm != null) {
            client.setRealmId(realmId);
            auditService.populateCreateAuditFields(client);
            return clientRepository.save(client);
        } else {
            // Handle the case where the provided realmId does not exist
            throw new RealmNotFoundException("Realm with ID " + client.getRealmId() + " not found");
        }
    }

    // Read
    public Page<Client> getAllClientsInRealm(String realmId, Pageable pageable) {
        // Assuming Client entity has a realm field
        return clientRepository.findAllByRealmId(realmId, pageable);
    }

    public Optional<Client> getClientInRealmById(String realmId, String id) {
        // Assuming Client entity has a realm field
        return clientRepository.findByIdAndRealmId(id, realmId);
    }

    // Update
    public Optional<Client> updateClientInRealm(String realmId, String id, Client updatedClient) {
        // Assuming Client entity has a realm field
        return clientRepository.findByIdAndRealmId(id, realmId).map(existingClient -> {
            existingClient.setName(updatedClient.getName());
            existingClient.setDescription(updatedClient.getDescription());
            // Set additional fields to update if needed
            auditService.populateCreateAuditFields(updatedClient);
            return clientRepository.save(existingClient);
        });
    }

    // Delete
    public void deleteClientInRealm(String realmId, String id) {
        // Assuming Client entity has a realm field
        clientRepository.deleteByIdAndRealmId(id, realmId);
    }


    // Exception for handling realm not found
    private static class RealmNotFoundException extends RuntimeException {
        public RealmNotFoundException(String message) {
            super(message);
        }
    }
}
