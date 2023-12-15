package com.openstrot.auth.realm.access.control.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.access.control.model.AccessRule;
import com.openstrot.auth.realm.access.control.repository.AccessProfileRepository;
import com.openstrot.auth.realm.access.control.repository.AccessRuleRepository;
import com.openstrot.auth.realm.repository.RealmRepository;
import com.openstrot.auth.realm.service.AuditService;

@Service
public class AccessRuleService {

	@Autowired private AccessRuleRepository accessRuleRepository;
    @Autowired private RealmRepository realmRepository;
    @Autowired private AccessProfileRepository accessProfileRepository;
	@Autowired private AuditService auditService;

    // Create
    public AccessRule createAccessRule(String realmId, String accessProfileId, AccessRule accessRule) {
        realmRepository.findById(realmId).orElseThrow(() -> new EntityNotFoundException("Realm not found"));
        accessProfileRepository.findById(accessProfileId).orElseThrow(() -> new EntityNotFoundException("AccessProfile not found"));

        accessRule.setRealmId(realmId);
        accessRule.setAccessProfileId(accessProfileId);

        auditService.populateCreateAuditFields(accessRule);
        return accessRuleRepository.save(accessRule);
    }

    // Read
    public Page<AccessRule> getAllAccessRuleInRealmAndProfile(String realmId, String accessProfileId, Pageable pageable) {
        return accessRuleRepository.findAllByRealmIdAndAccessProfileId(realmId, accessProfileId, pageable);
    }

    public Optional<AccessRule> getAccessRuleInRealmAndProfileById(String realmId, String accessProfileId, String id) {
        return accessRuleRepository.findByIdAndRealmIdAndAccessProfileId(id, realmId, accessProfileId);
    }

    // Update
    public Optional<AccessRule> updateAccessRuleInRealmAndProfile(String realmId, String accessProfileId, String id, AccessRule updatedAccessRule) {
        return accessRuleRepository.findByIdAndRealmIdAndAccessProfileId(id, realmId, accessProfileId).map(existingAccessRule -> {
        	existingAccessRule.setPermissionId(updatedAccessRule.getPermissionId());
        	existingAccessRule.setRoleId(updatedAccessRule.getRoleId());
        	existingAccessRule.setResourceId(updatedAccessRule.getResourceId());
            existingAccessRule.setMetadata(updatedAccessRule.getMetadata());
            auditService.populateCreateAuditFields(updatedAccessRule);
            return accessRuleRepository.save(existingAccessRule);
        });
    }

    // Delete
    public void deleteAccessRuleInRealmAndProfile(String realmId, String accessProfileId, String id) {
        accessRuleRepository.deleteByIdAndRealmIdAndAccessProfileId(id, realmId, accessProfileId);
        // TODO: Implement logic to check if associated with any user before deletion
    }

    // Exception for handling entity not found
    private static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }
}
