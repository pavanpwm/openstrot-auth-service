package com.openstrot.auth.realm.client.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openstrot.auth.realm.model.Auditable;

@Document(collection = "clients")
@CompoundIndex(name = "unique_name_realm_id", def = "{'name': 1, 'realm_id': 1}", unique = true)
public class Client extends Auditable {

	@Id
	private String id;

	private String name;
	private String description;
	private boolean enabled;
	private boolean isPublic;
	private String secret;
    @Field("realm_id")
    @Indexed
    @JsonIgnore //not accepting for object creation | better use URL path parameters instead
	private String realmId;

	public Client() {
		// Default constructor
	}

	public Client(String name, String description, boolean enabled, boolean isPublic, String secret, String realmId) {
		this.name = name;
		this.description = description;
		this.enabled = enabled;
		this.isPublic = isPublic;
		this.secret = secret;
		this.realmId = realmId;
	}

	// Getters and Setters

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean aPublic) {
		isPublic = aPublic;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

}
