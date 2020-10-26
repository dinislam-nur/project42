package ru.innopolis.stc27.maslakov.enterprise.project42.services.session;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SessionDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DBSessionService implements SessionService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final TableRepository tableRepository;

    @Override
    @Transactional
    public Optional<SessionDTO> loginWithCredentials(CredentialsDTO credentials, UUID tableId) {
        boolean isAnonymous = false;
        User user;
        if (credentials == null) {
            isAnonymous = true;
            user = userRepository.findByLogin("anonymous")
                    .orElseThrow(() -> new IllegalArgumentException("Неправильный логин или пароль"));
        } else {
            user = userRepository.findByLogin(credentials.getLogin().toLowerCase())
                    .orElseThrow(() -> new IllegalArgumentException("Неправильный логин или пароль"));
        }
        if (isAnonymous || encoder.matches(credentials.getPassword(), user.getPassword())) {
            if (user.getRole() == Role.ROLE_GUEST) {
                val table = tableRepository.findById(tableId).orElseThrow(() -> new IllegalArgumentException("Такого стола не существует"));
                return getSessionDTO(user, table);
            } else {
                return getSessionDTO(user, null);
            }
        }
        return Optional.empty();
    }

    private Optional<SessionDTO> getSessionDTO(User user, Table table) {
        val session = sessionRepository
                .findByUser(user)
                .filter(this::deleteSessionIfOverdue)
                .orElseGet(() -> createSession(user, table));
        return Optional.of(DTOConverter.convertToDTO(session));
    }

    private Session createSession(User user, Table table) {
        val newSession = Session.builder()
                .status(SessionStatus.OPENED)
                .table(table)
                .token(UUID.randomUUID().toString())
                .user(user)
                .build();
        sessionRepository.save(newSession);
        return newSession;
    }

    private boolean deleteSessionIfOverdue(Session oldSession) {
        val notOverdue = oldSession.getTimeout().after(Timestamp.valueOf(LocalDateTime.now()));
        if (!notOverdue) {
            sessionRepository.delete(oldSession);
        }
        return notOverdue;
    }

    @Override
    @Transactional
    public boolean logout(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        session.ifPresent(sessionRepository::delete);
        return true;
    }
}
