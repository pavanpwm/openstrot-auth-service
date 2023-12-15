package com.openstrot.auth.realm.access.control.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.access.control.model.Resource;
import com.openstrot.auth.realm.access.control.repository.ResourceRepository;
import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;
import com.openstrot.auth.realm.service.AuditService;

@Service
public class ResourceService {

	@Autowired private ResourceRepository resourceRepository;
	@Autowired private RealmRepository realmRepository;
	@Autowired private AuditService auditService;

    // Create
    public Resource createResource(String realmId, Resource resource) {
        // Validate that the provided realmId exists
        Realm realm = realmRepository.findById(realmId).orElse(null);
        if (realm != null) {
            resource.setRealmId(realmId);
            auditService.populateCreateAuditFields(resource);
            return resourceRepository.save(resource);
        } else {
            // Handle the case where the provided realmId does not exist
            throw new RealmNotFoundException("Realm with ID " + realmId + " not found");
        }
    }

    // Read
    public Page<Resource> getAllResourcesInRealm(String realmId, Pageable pageable) {
        return resourceRepository.findAllByRealmId(realmId, pageable);
    }

    public Optional<Resource> getResourceInRealmById(String realmId, String id) {
        return resourceRepository.findByIdAndRealmId(id, realmId);
    }

    // Update
    public Optional<Resource> updateResourceInRealm(String realmId, String id, Resource updatedResource) {
        return resourceRepository.findByIdAndRealmId(id, realmId).map(existingResource -> {
            existingResource.setName(updatedResource.getName());
            existingResource.setDescription(updatedResource.getDescription());
            // Set additional fields to update if needed
            auditService.populateCreateAuditFields(updatedResource);
            return resourceRepository.save(existingResource);
        });
    }

    // Delete
    public void deleteResourceInRealm(String realmId, String id) {
        resourceRepository.deleteByIdAndRealmId(id, realmId);
        //TODO do not allow delete if associated with any user
    }

    // Exception for handling realm not found
    private static class RealmNotFoundException extends RuntimeException {
        public RealmNotFoundException(String message) {
            super(message);
        }
    }
}
