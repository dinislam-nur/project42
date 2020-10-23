package ru.innopolis.stc27.maslakov.enterprise.project42.services.session;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SessionDTO;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {

    Optional<SessionDTO> loginWithCredentials(CredentialsDTO credentials, UUID tableId);

    Optional<SessionDTO> loginWithToken(String token);

    boolean logout(String token);

}
