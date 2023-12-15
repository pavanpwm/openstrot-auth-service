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

import com.openstrot.auth.realm.access.control.model.Resource;
import com.openstrot.auth.realm.access.control.service.ResourceService;

@RestController
@RequestMapping("/api/realms/{realmId}/resources")
public class ResourceController {

	@Autowired private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Resource> createResource(@PathVariable String realmId, @RequestBody Resource resource) {
        Resource createdResource = resourceService.createResource(realmId, resource);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Resource>> getAllResources(@PathVariable String realmId,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Resource> resourcesPage = resourceService.getAllResourcesInRealm(realmId, pageable);
        return new ResponseEntity<>(resourcesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable String realmId, @PathVariable String id) {
        Optional<Resource> resource = resourceService.getResourceInRealmById(realmId, id);
        return resource.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable String realmId, @PathVariable String id, @RequestBody Resource updatedResource) {
        Optional<Resource> resource = resourceService.updateResourceInRealm(realmId, id, updatedResource);
        return resource.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteResource(@PathVariable String realmId, @PathVariable String id) {
        resourceService.deleteResourceInRealm(realmId, id);
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
