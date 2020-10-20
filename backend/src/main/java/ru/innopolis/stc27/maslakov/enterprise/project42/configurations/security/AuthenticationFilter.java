package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final SessionRepository sessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            log.debug("doFilterInternal: token is null.");
            return;
        }

        try {
            Session session = sessionRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Session not found"));
            TokenAuthentication authRequest = new TokenAuthentication(session);
            Authentication authResult = this.authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (AuthenticationException | IllegalArgumentException e) {
            log.debug("doFilterInternal: Exception caught: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
