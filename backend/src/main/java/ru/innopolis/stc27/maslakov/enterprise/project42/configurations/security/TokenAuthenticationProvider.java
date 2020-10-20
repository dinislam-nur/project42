package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;

import java.util.Collections;

@AllArgsConstructor
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final SessionRepository sessionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val o = authentication.getCredentials().toString();
        val session = sessionRepository.findByToken(o).orElseThrow(()-> new BadCredentialsException("Сессия не найдена"));
        return new UsernamePasswordAuthenticationToken(
                session.getUser().getLogin(),
                session,
                Collections.singletonList(
                        new SimpleGrantedAuthority(session.getUser().getRole().toString())
                )
        );
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(TokenAuthentication.class);
    }
}
