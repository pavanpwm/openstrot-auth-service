package com.openstrot.auth.realm.client.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.openstrot.auth.realm.client.model.WebOrigin;

public interface WebOriginRepository extends MongoRepository<WebOrigin, String> {

	Optional<WebOrigin> findByIdAndRealmId(String id, String realmId);

	Page<WebOrigin> findAllByRealmId(String realmId, Pageable pageable);

	void deleteByIdAndRealmId(String id, String realmId);
}
