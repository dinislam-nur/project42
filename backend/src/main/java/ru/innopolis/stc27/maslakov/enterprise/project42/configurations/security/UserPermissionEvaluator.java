package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;

import java.io.Serializable;

public class UserPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String target, Object permission) {
        return "order".equals(target) &&
                "get".equals(permission.toString()) &&
                ((Session) authentication.getDetails()).getUser().getId().equals(targetId);
    }
}
