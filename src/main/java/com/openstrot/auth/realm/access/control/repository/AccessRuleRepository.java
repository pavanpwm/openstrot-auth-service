package com.openstrot.auth.realm.access.control.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.openstrot.auth.realm.access.control.model.AccessRule;

public interface AccessRuleRepository extends MongoRepository<AccessRule, String> {

    Page<AccessRule> findAllByRealmIdAndAccessProfileId(String realmId, String accessProfileId, Pageable pageable);

    Optional<AccessRule> findByIdAndRealmIdAndAccessProfileId(String id, String realmId, String accessProfileId);

    void deleteByIdAndRealmIdAndAccessProfileId(String id, String realmId, String accessProfileId);

}
