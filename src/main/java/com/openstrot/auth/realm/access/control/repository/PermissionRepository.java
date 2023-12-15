package com.openstrot.auth.realm.access.control.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.openstrot.auth.realm.access.control.model.Permission;

public interface PermissionRepository extends MongoRepository<Permission, String> {

    Page<Permission> findAllByRealmId(String realmId, Pageable pageable);

    Optional<Permission> findByIdAndRealmId(String id, String realmId);

    void deleteByIdAndRealmId(String id, String realmId);

}
