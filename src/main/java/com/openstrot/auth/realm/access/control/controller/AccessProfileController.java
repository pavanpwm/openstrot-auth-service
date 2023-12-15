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

import com.openstrot.auth.realm.access.control.model.AccessProfile;
import com.openstrot.auth.realm.access.control.service.AccessProfileService;

@RestController
@RequestMapping("/api/realms/{realmId}/access-profiles")
public class AccessProfileController {

	@Autowired private AccessProfileService accessProfileService;

    @PostMapping
    public ResponseEntity<AccessProfile> createAccessProfile(@PathVariable String realmId, @RequestBody AccessProfile accessProfile) {
        AccessProfile createdAccessProfile = accessProfileService.createAccessProfile(realmId, accessProfile);
        return new ResponseEntity<>(createdAccessProfile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AccessProfile>> getAllAccessProfiles(@PathVariable String realmId,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                   @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<AccessProfile> accessProfilesPage = accessProfileService.getAllAccessProfilesInRealm(realmId, pageable);
        return new ResponseEntity<>(accessProfilesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessProfile> getAccessProfileById(@PathVariable String realmId, @PathVariable String id) {
        Optional<AccessProfile> accessProfile = accessProfileService.getAccessProfileInRealmById(realmId, id);
        return accessProfile.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessProfile> updateAccessProfile(@PathVariable String realmId, @PathVariable String id, @RequestBody AccessProfile updatedAccessProfile) {
        Optional<AccessProfile> accessProfile = accessProfileService.updateAccessProfileInRealm(realmId, id, updatedAccessProfile);
        return accessProfile.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteAccessProfile(@PathVariable String realmId, @PathVariable String id) {
        accessProfileService.deleteAccessProfileInRealm(realmId, id);
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
