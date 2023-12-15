package com.openstrot.auth.realm.access.control.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.access.control.model.AccessProfile;
import com.openstrot.auth.realm.access.control.repository.AccessProfileRepository;
import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;
import com.openstrot.auth.realm.service.AuditService;

@Service
public class AccessProfileService {

	@Autowired private AccessProfileRepository accessProfileRepository;
    @Autowired private RealmRepository realmRepository;
	@Autowired private AuditService auditService;

    // Create
    public AccessProfile createAccessProfile(String realmId, AccessProfile accessProfile) {
        // Validate that the provided realmId exists
        Realm realm = realmRepository.findById(realmId).orElse(null);
        if (realm != null) {
            accessProfile.setRealmId(realmId);
            auditService.populateCreateAuditFields(accessProfile);
            return accessProfileRepository.save(accessProfile);
        } else {
            // Handle the case where the provided realmId does not exist
            throw new RealmNotFoundException("Realm with ID " + realmId + " not found");
        }
    }

    // Read
    public Page<AccessProfile> getAllAccessProfilesInRealm(String realmId, Pageable pageable) {
        return accessProfileRepository.findAllByRealmId(realmId, pageable);
    }

    public Optional<AccessProfile> getAccessProfileInRealmById(String realmId, String id) {
        return accessProfileRepository.findByIdAndRealmId(id, realmId);
    }

    // Update
    public Optional<AccessProfile> updateAccessProfileInRealm(String realmId, String id, AccessProfile updatedAccessProfile) {
        return accessProfileRepository.findByIdAndRealmId(id, realmId).map(existingAccessProfile -> {
            existingAccessProfile.setName(updatedAccessProfile.getName());
            existingAccessProfile.setDescription(updatedAccessProfile.getDescription());
            // Set additional fields to update if needed
            auditService.populateCreateAuditFields(updatedAccessProfile);
            return accessProfileRepository.save(existingAccessProfile);
        });
    }

    // Delete
    public void deleteAccessProfileInRealm(String realmId, String id) {
        accessProfileRepository.deleteByIdAndRealmId(id, realmId);
        //TODO do not allow delete if associated with any user
    }

    // Exception for handling realm not found
    private static class RealmNotFoundException extends RuntimeException {
        public RealmNotFoundException(String message) {
            super(message);
        }
    }
}
