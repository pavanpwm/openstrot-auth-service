package com.openstrot.auth.realm.client.controller;

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

import com.openstrot.auth.realm.client.model.WebOrigin;
import com.openstrot.auth.realm.client.service.WebOriginService;

@RestController
@RequestMapping("/api/realms/{realmId}/web-origins")
public class WebOriginController {

	@Autowired private WebOriginService webOriginService;

    @PostMapping
    public ResponseEntity<WebOrigin> createWebOrigin(@PathVariable String realmId, @RequestBody WebOrigin webOrigin) {
        WebOrigin createdWebOrigin = webOriginService.createWebOrigin(realmId, webOrigin);
        return new ResponseEntity<>(createdWebOrigin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<WebOrigin>> getAllWebOrigins(@PathVariable String realmId,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<WebOrigin> webOriginsPage = webOriginService.getAllWebOriginsInRealm(realmId, pageable);
        return new ResponseEntity<>(webOriginsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebOrigin> getWebOriginById(@PathVariable String realmId, @PathVariable String id) {
        Optional<WebOrigin> webOrigin = webOriginService.getWebOriginInRealmById(realmId, id);
        return webOrigin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebOrigin> updateWebOrigin(@PathVariable String realmId, @PathVariable String id, @RequestBody WebOrigin updatedWebOrigin) {
        Optional<WebOrigin> webOrigin = webOriginService.updateWebOriginInRealm(realmId, id, updatedWebOrigin);
        return webOrigin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteWebOrigin(@PathVariable String realmId, @PathVariable String id) {
        webOriginService.deleteWebOriginInRealm(realmId, id);
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
