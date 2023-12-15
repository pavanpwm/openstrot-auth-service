package com.openstrot.auth.realm.access.control.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.access.control.model.Role;
import com.openstrot.auth.realm.access.control.repository.RoleRepository;
import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.repository.RealmRepository;
import com.openstrot.auth.realm.service.AuditService;

@Service
public class RoleService {
	
	@Autowired private RoleRepository roleRepository;
	@Autowired private RealmRepository realmRepository;
	@Autowired private AuditService auditService;

    // Create
    public Role createRole(String realmId, Role role) {
        // Validate that the provided realmId exists
        Realm realm = realmRepository.findById(realmId).orElse(null);
        if (realm != null) {
            role.setRealmId(realmId);
            auditService.populateCreateAuditFields(role);
            return roleRepository.save(role);
        } else {
            // Handle the case where the provided realmId does not exist
            throw new RealmNotFoundException("Realm with ID " + realmId + " not found");
        }
    }

    // Read
    public Page<Role> getAllRolesInRealm(String realmId, Pageable pageable) {
        return roleRepository.findAllByRealmId(realmId, pageable);
    }

    public Optional<Role> getRoleInRealmById(String realmId, String id) {
        return roleRepository.findByIdAndRealmId(id, realmId);
    }

    // Update
    public Optional<Role> updateRoleInRealm(String realmId, String id, Role updatedRole) {
        return roleRepository.findByIdAndRealmId(id, realmId).map(existingRole -> {
            existingRole.setName(updatedRole.getName());
            existingRole.setDescription(updatedRole.getDescription());
            // Set additional fields to update if needed
            auditService.populateCreateAuditFields(updatedRole);
            return roleRepository.save(existingRole);
        });
    }

    // Delete
    public void deleteRoleInRealm(String realmId, String id) {
        roleRepository.deleteByIdAndRealmId(id, realmId);
        //TODO do not allow delete if associated with any user
    }

    // Exception for handling realm not found
    private static class RealmNotFoundException extends RuntimeException {
        public RealmNotFoundException(String message) {
            super(message);
        }
    }
}
