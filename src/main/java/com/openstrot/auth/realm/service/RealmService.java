package com.openstrot.auth.realm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;

@Service
public class RealmService {

	@Autowired private RealmRepository realmRepository;
	@Autowired private AuditService auditService;
	
    // Create
    public Realm createRealm(Realm realm) {
        // Set additional fields like createdAt and modifiedAt if needed
    	auditService.populateCreateAuditFields(realm);
        return realmRepository.save(realm);
    }

    // Read
    public Page<Realm> getAllRealms(Pageable pageable) {
        return realmRepository.findAll(pageable);
    }

    public Optional<Realm> getRealmById(String id) {
        return realmRepository.findById(id);
    }

    // Update
    public Optional<Realm> updateRealm(String id, Realm updatedRealm) {
        return realmRepository.findById(id).map(existingRealm -> {
            existingRealm.setName(updatedRealm.getName());
            existingRealm.setEnabled(updatedRealm.isEnabled());
            auditService.populateCreateAuditFields(updatedRealm);
            // Set additional fields to update if needed
            return realmRepository.save(existingRealm);
        });
    }

    // Delete
    public void deleteRealm(String id) {
        realmRepository.deleteById(id); //TODO re check all delete methods
    }
}
