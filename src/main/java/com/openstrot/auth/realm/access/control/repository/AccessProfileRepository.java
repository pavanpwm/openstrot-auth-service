package com.openstrot.auth.realm.access.control.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.openstrot.auth.realm.access.control.model.AccessProfile;

public interface AccessProfileRepository extends MongoRepository<AccessProfile, String> {

    Page<AccessProfile> findAllByRealmId(String realmId, Pageable pageable);

    Optional<AccessProfile> findByIdAndRealmId(String id, String realmId);

    void deleteByIdAndRealmId(String id, String realmId);

}
