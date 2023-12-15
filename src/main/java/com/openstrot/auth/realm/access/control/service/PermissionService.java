package com.openstrot.auth.realm.access.control.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.access.control.model.Permission;
import com.openstrot.auth.realm.access.control.repository.PermissionRepository;
import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;
import com.openstrot.auth.realm.service.AuditService;

@Service
public class PermissionService {

	@Autowired private PermissionRepository permissionRepository;
	@Autowired private RealmRepository realmRepository;
	@Autowired private AuditService auditService;

    // Create
    public Permission createPermission(String realmId, Permission permission) {
        // Validate that the provided realmId exists
        Realm realm = realmRepository.findById(realmId).orElse(null);
        if (realm != null) {
            permission.setRealmId(realmId);
            auditService.populateCreateAuditFields(permission);
            return permissionRepository.save(permission);
        } else {
            // Handle the case where the provided realmId does not exist
            throw new RealmNotFoundException("Realm with ID " + realmId + " not found");
        }
    }

    // Read
    public Page<Permission> getAllPermissionsInRealm(String realmId, Pageable pageable) {
        return permissionRepository.findAllByRealmId(realmId, pageable);
    }

    public Optional<Permission> getPermissionInRealmById(String realmId, String id) {
        return permissionRepository.findByIdAndRealmId(id, realmId);
    }

    // Update
    public Optional<Permission> updatePermissionInRealm(String realmId, String id, Permission updatedPermission) {
        return permissionRepository.findByIdAndRealmId(id, realmId).map(existingPermission -> {
            existingPermission.setName(updatedPermission.getName());
            // Set additional fields to update if needed
            auditService.populateCreateAuditFields(updatedPermission);
            return permissionRepository.save(existingPermission);
        });
    }

    // Delete
    public void deletePermissionInRealm(String realmId, String id) {
        permissionRepository.deleteByIdAndRealmId(id, realmId);
        //TODO do not allow delete if associated with any user
    }

    // Exception for handling realm not found
    private static class RealmNotFoundException extends RuntimeException {
        public RealmNotFoundException(String message) {
            super(message);
        }
    }
}
