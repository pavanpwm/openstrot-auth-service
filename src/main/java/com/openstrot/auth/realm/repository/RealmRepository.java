package com.openstrot.auth.realm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.openstrot.auth.realm.model.Realm;

@Repository
public interface RealmRepository extends MongoRepository<Realm, String> {

}
