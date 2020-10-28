package ru.innopolis.stc27.maslakov.enterprise.project42.services.session;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SessionDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
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
    public static final String ANON_PASSWORD = "anonymous";

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
            user = User.builder()
                    .login(UUID.randomUUID().toString())
                    .password(ANON_PASSWORD)
                    .role(Role.ROLE_GUEST)
                    .build();
            user = userRepository.save(user);
        } else {
            user = userRepository.findByLogin(credentials.getLogin().toLowerCase())
                    .orElseThrow(() -> new IllegalStateException("Неправильный логин или пароль"));
        }
        if (isAnonymous || encoder.matches(credentials.getPassword(), user.getPassword())) {
            if (user.getRole() == Role.ROLE_GUEST) {
                val table = tableRepository.findById(tableId)
                        .orElseThrow(() -> new IllegalStateException("Такого стола не существует"));
                openTable(table);
                return getSessionDTO(user, table);
            } else {
                return getSessionDTO(user, null);
            }
        }
        return Optional.empty();
    }

    private void openTable(Table table) {
        if (table.getStatus().equals(TableStatus.NOT_RESERVED)) {
            table.setStatus(TableStatus.RESERVED);
            tableRepository.save(table);
        }
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
        sessionRepository.findByToken(token)
                .ifPresent(this::deleteAnonymousOrSession);
        return true;
    }

    public void deleteAnonymousOrSession(Session session) {
        val user = session.getUser();
        if (ANON_PASSWORD.equals(user.getPassword())) {
            userRepository.delete(user);
        } else {
            sessionRepository.delete(session);
        }
    }
}
