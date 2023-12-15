package com.openstrot.auth.realm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openstrot.auth.realm.model.Auditable;
import com.openstrot.auth.util.DateTimeUtil;

@Service
public class AuditService {

	@Autowired private AuditorProviderService auditorProviderService;
	
    public <T extends Auditable> void populateCreateAuditFields(T auditable) {
        String username = auditorProviderService.getCurrentAuditor();
        long currentMillis = DateTimeUtil.getCurrentUtcTimeMillis();
        auditable.setCreatedBy(username);
        auditable.setCreatedAt(currentMillis);
        auditable.setLastModifiedBy(username);
        auditable.setLastModifiedAt(currentMillis);
    }

    public <T extends Auditable> void populateUpdateAuditFields(T auditable) {
        String username = auditorProviderService.getCurrentAuditor();
        long currentMillis = DateTimeUtil.getCurrentUtcTimeMillis();
        auditable.setLastModifiedBy(username);
        auditable.setLastModifiedAt(currentMillis);
    }

}
