package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;

import java.util.Collection;
import java.util.Collections;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final String login;

    public TokenAuthentication(Session session) {
        super(Collections.emptyList());
        setAuthenticated(false);
        setDetails(session);
        login = "";
    }

    public TokenAuthentication(String login,
                               Session session,
                               Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.setAuthenticated(true);
        this.setDetails(session);
        this.login = login;
    }

    @Override
    public Object getCredentials() {
        return getDetails();
    }

    @Override
    public Object getPrincipal() {
        return login;
    }
}
