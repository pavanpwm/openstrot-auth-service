package com.openstrot.auth.realm.client.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.openstrot.auth.realm.client.model.Client;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

	Optional<Client> findByIdAndRealmId(String id, String realmId);

	Page<Client> findAllByRealmId(String realmId, Pageable pageable);

	void deleteByIdAndRealmId(String id, String realmId);
}
