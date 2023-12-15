package com.openstrot.auth.realm.access.control.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.openstrot.auth.realm.access.control.model.Resource;

public interface ResourceRepository extends MongoRepository<Resource, String> {

    Page<Resource> findAllByRealmId(String realmId, Pageable pageable);

    Optional<Resource> findByIdAndRealmId(String id, String realmId);

    void deleteByIdAndRealmId(String id, String realmId);

}
