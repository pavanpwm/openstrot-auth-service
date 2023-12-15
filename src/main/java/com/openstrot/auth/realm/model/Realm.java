package com.openstrot.auth.realm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "realms")
public class Realm extends Auditable {

	@Id
	private String id;
	@Indexed(unique = true)
	private String name;
	private boolean enabled;


	public Realm() {
	}

	public Realm(String name, boolean enabled) {
		this.name = name;
		this.enabled = enabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
