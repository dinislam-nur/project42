package ru.innopolis.stc27.maslakov.enterprise.project42.services.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SessionDTO;

import java.util.Optional;

public interface AuthenticationService extends UserDetailsService {

    Optional<SessionDTO> loginWithCredentials(CredentialsDTO credentials, Long tableId);

    Optional<SessionDTO> loginWithToken(String token);

    boolean logout(String token);

}
