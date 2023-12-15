package com.openstrot.auth.realm.access.control.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.openstrot.auth.realm.access.control.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

	Page<Role> findAllByRealmId(String realmId, Pageable pageable);

	Optional<Role> findByIdAndRealmId(String id, String realmId);

	void deleteByIdAndRealmId(String id, String realmId);

}
