package com.openstrot.auth.realm.client.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openstrot.auth.realm.model.Auditable;

@Document(collection = "web_origins")
@CompoundIndex(name = "unique_name_realm_id", def = "{'name': 1, 'realm_id': 1}", unique = true)
public class WebOrigin extends Auditable {

    @Id
    private String id;
    private String origin;
    @Field("realm_id")
    @Indexed
    @JsonIgnore //not accepting for object creation | better use URL path parameters instead
    private String realmId;

    public WebOrigin() {
    }

    public WebOrigin(String origin, String realmId) {
        this.origin = origin;
        this.realmId = realmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

}
