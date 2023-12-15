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

import com.openstrot.auth.realm.access.control.model.Permission;
import com.openstrot.auth.realm.access.control.service.PermissionService;

@RestController
@RequestMapping("/api/realms/{realmId}/permissions")
public class PermissionController {

	@Autowired private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Permission> createPermission(@PathVariable String realmId, @RequestBody Permission permission) {
        Permission createdPermission = permissionService.createPermission(realmId, permission);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Permission>> getAllPermissions(@PathVariable String realmId,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Permission> permissionsPage = permissionService.getAllPermissionsInRealm(realmId, pageable);
        return new ResponseEntity<>(permissionsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable String realmId, @PathVariable String id) {
        Optional<Permission> permission = permissionService.getPermissionInRealmById(realmId, id);
        return permission.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable String realmId, @PathVariable String id, @RequestBody Permission updatedPermission) {
        Optional<Permission> permission = permissionService.updatePermissionInRealm(realmId, id, updatedPermission);
        return permission.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable String realmId, @PathVariable String id) {
        permissionService.deletePermissionInRealm(realmId, id);
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
