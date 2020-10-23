package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;

import java.util.Collections;

@Slf4j
@Component
@AllArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val o = authentication.getCredentials();
        if(o instanceof Session) {
            val session = (Session) o;
            return new UsernamePasswordAuthenticationToken(
                    session.getUser().getLogin(),
                    session,
                    Collections.singletonList(
                            new SimpleGrantedAuthority(session.getUser().getRole().toString())
                    )
            );
        } else {
            log.debug("authenticate: Wrong credentials {}", o);
            throw new BadCredentialsException("Неправильный токен");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(TokenAuthentication.class);
    }
}
