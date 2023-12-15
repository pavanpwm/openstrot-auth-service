package com.openstrot.auth.realm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class AuditorProviderService  {

    public String getCurrentAuditor() {
        //TODO
        return "";
    }
}
