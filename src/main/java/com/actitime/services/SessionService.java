package com.actitime.services;

import com.actitime.domain.Role;
import com.actitime.utils.CustomUserDetails;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SessionService {
    @Inject
    private SessionRegistry sessionRegistry;

    @Secured({Role.ROLE_MANAGER})
    public void expireSession(Long userId) {
        CustomUserDetails userDetails = null;
        for (Object principal : sessionRegistry.getAllPrincipals())
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails_ = (CustomUserDetails) principal;
                if (userDetails_.getId().equals(userId)) {
                    userDetails = userDetails_;
                    break;
                }
            }

        if (userDetails != null)
            for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(userDetails, false))
                sessionInformation.expireNow();
    }
}
