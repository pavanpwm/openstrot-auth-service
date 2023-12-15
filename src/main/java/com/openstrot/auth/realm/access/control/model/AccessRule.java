package com.openstrot.auth.realm.access.control.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openstrot.auth.realm.model.Auditable;

@Document(collection = "access_rules")
public class AccessRule extends Auditable {

	@Id
	private String id;

	@Field("role_id")
	private String roleId;

	@Field("resource_id")
	private String resourceId;

	@Field("permission_id")
	private String permissionId;

	private String metadata;

	@Field("access_profile_id")
	@Indexed
	@JsonIgnore // not accepting for object creation | better use URL path parameters instead
	private String accessProfileId;

	@Field("realm_id")
	@Indexed
	@JsonIgnore // not accepting for object creation | better use URL path parameters instead
	private String realmId;

	public AccessRule() {
	}

	public AccessRule(String roleId, String resourceId, String permissionId, String metadata, String accessProfileId,
			String realmId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
		this.permissionId = permissionId;
		this.metadata = metadata;
		this.accessProfileId = accessProfileId;
		this.realmId = realmId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getAccessProfileId() {
		return accessProfileId;
	}

	public void setAccessProfileId(String accessProfileId) {
		this.accessProfileId = accessProfileId;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
}
