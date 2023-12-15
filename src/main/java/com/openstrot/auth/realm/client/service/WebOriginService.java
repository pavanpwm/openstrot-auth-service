package com.openstrot.auth.realm.client.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.client.model.WebOrigin;
import com.openstrot.auth.realm.client.repository.WebOriginRepository;
import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;

@Service
public class WebOriginService {

	@Autowired private WebOriginRepository webOriginRepository;
    @Autowired private RealmRepository realmRepository;

    // Create
    public WebOrigin createWebOrigin(String realmId, WebOrigin webOrigin) {
        // Validate that the provided realmId exists
        Realm realm = realmRepository.findById(realmId).orElse(null);
        if (realm != null) {
            webOrigin.setRealmId(realmId);
            return webOriginRepository.save(webOrigin);
        } else {
            // Handle the case where the provided realmId does not exist
            throw new RealmNotFoundException("Realm with ID " + realmId + " not found");
        }
    }

    // Read
    public Page<WebOrigin> getAllWebOriginsInRealm(String realmId, Pageable pageable) {
        return webOriginRepository.findAllByRealmId(realmId, pageable);
    }

    public Optional<WebOrigin> getWebOriginInRealmById(String realmId, String id) {
        return webOriginRepository.findByIdAndRealmId(id, realmId);
    }

    // Update
    public Optional<WebOrigin> updateWebOriginInRealm(String realmId, String id, WebOrigin updatedWebOrigin) {
        return webOriginRepository.findByIdAndRealmId(id, realmId).map(existingWebOrigin -> {
            existingWebOrigin.setOrigin(updatedWebOrigin.getOrigin());
            // Set additional fields to update if needed
            return webOriginRepository.save(existingWebOrigin);
        });
    }

    // Delete
    public void deleteWebOriginInRealm(String realmId, String id) {
        webOriginRepository.deleteByIdAndRealmId(id, realmId);
        //TODO do not allow delete if associated with any client
    }

    // Exception for handling realm not found
    private static class RealmNotFoundException extends RuntimeException {
        public RealmNotFoundException(String message) {
            super(message);
        }
    }
}
