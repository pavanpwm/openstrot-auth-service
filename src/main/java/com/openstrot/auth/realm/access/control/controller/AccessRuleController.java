package com.openstrot.auth.realm.access.control.controller;

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

import com.openstrot.auth.realm.access.control.model.AccessRule;
import com.openstrot.auth.realm.access.control.service.AccessRuleService;

@RestController
@RequestMapping("/api/realms/{realmId}/access-profiles/{accessProfileId}/access-rules")
public class AccessRuleController {

	@Autowired private AccessRuleService accessRuleService;

    @PostMapping
    public ResponseEntity<AccessRule> createAccessRule(
            @PathVariable String realmId,
            @PathVariable String accessProfileId,
            @RequestBody AccessRule accessRule) {
        AccessRule createdAccessRule = accessRuleService.createAccessRule(realmId, accessProfileId, accessRule);
        return new ResponseEntity<>(createdAccessRule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AccessRule>> getAllAccessMatrices(
            @PathVariable String realmId,
            @PathVariable String accessProfileId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<AccessRule> accessMatricesPage = accessRuleService.getAllAccessRuleInRealmAndProfile(realmId, accessProfileId, pageable);
        return new ResponseEntity<>(accessMatricesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessRule> getAccessRuleById(
            @PathVariable String realmId,
            @PathVariable String accessProfileId,
            @PathVariable String id) {
        Optional<AccessRule> accessRule = accessRuleService.getAccessRuleInRealmAndProfileById(realmId, accessProfileId, id);
        return accessRule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessRule> updateAccessRule(
            @PathVariable String realmId,
            @PathVariable String accessProfileId,
            @PathVariable String id,
            @RequestBody AccessRule updatedAccessRule) {
        Optional<AccessRule> accessRule = accessRuleService.updateAccessRuleInRealmAndProfile(realmId, accessProfileId, id, updatedAccessRule);
        return accessRule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteAccessRule(
            @PathVariable String realmId,
            @PathVariable String accessProfileId,
            @PathVariable String id) {
        accessRuleService.deleteAccessRuleInRealmAndProfile(realmId, accessProfileId, id);
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
