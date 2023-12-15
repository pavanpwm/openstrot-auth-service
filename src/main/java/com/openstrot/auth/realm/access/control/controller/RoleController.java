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

import com.openstrot.auth.realm.access.control.model.Role;
import com.openstrot.auth.realm.access.control.service.RoleService;

@RestController
@RequestMapping("/api/realms/{realmId}/roles")
public class RoleController {

	@Autowired private RoleService roleService;
 
	@PostMapping
    public ResponseEntity<Role> createRole(@PathVariable String realmId, @RequestBody Role role) {
        Role createdRole = roleService.createRole(realmId, role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getAllRoles(@PathVariable String realmId,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Role> rolesPage = roleService.getAllRolesInRealm(realmId, pageable);
        return new ResponseEntity<>(rolesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable String realmId, @PathVariable String id) {
        Optional<Role> role = roleService.getRoleInRealmById(realmId, id);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable String realmId, @PathVariable String id, @RequestBody Role updatedRole) {
        Optional<Role> role = roleService.updateRoleInRealm(realmId, id, updatedRole);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable String realmId, @PathVariable String id) {
        roleService.deleteRoleInRealm(realmId, id);
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
