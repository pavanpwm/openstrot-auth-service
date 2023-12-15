package com.openstrot.auth.realm.controller;

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

import com.openstrot.auth.realm.model.Realm;
import com.openstrot.auth.realm.service.RealmService;

@RestController
@RequestMapping("/api/realms")
public class RealmController {

	@Autowired private RealmService realmService;

    @PostMapping
    public ResponseEntity<Realm> createRealm(@RequestBody Realm realm) {
        Realm createdRealm = realmService.createRealm(realm);
        return new ResponseEntity<>(createdRealm, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Realm>> getAllRealms(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                    @RequestParam(value = "sort", defaultValue = "created_at,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<Realm> realmsPage = realmService.getAllRealms(pageable);
        return new ResponseEntity<>(realmsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Realm> getRealmById(@PathVariable String id) {
        Optional<Realm> realm = realmService.getRealmById(id);
        return realm.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Realm> updateRealm(@PathVariable String id, @RequestBody Realm updatedRealm) {
        Optional<Realm> realm = realmService.updateRealm(id, updatedRealm);
        return realm.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteRealm(@PathVariable String id) {
        realmService.deleteRealm(id);
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
