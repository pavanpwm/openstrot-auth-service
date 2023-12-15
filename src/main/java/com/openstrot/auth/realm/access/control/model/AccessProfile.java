package com.openstrot.auth.realm.access.control.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openstrot.auth.realm.model.Auditable;

@Document(collection = "access_profiles")
@CompoundIndex(name = "unique_name_realm_id", def = "{'name': 1, 'realm_id': 1}", unique = true)
public class AccessProfile extends Auditable {

	@Id
	private String id;
	private String name;
	private String description;
	@Field("realm_id")
	@JsonIgnore //not accepting for object creation | better use URL path parameters instead
	@Indexed
	private String realmId;

	public AccessProfile() {
	}

	public AccessProfile(String id, String name, String description, String realmId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.realmId = realmId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
}
