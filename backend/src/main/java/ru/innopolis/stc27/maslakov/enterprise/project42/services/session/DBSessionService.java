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
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.session.SessionService;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

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
        val user = userRepository.findByLogin(credentials.getLogin()).orElseThrow(() -> new IllegalArgumentException("Неправильный логин или пароль"));
        val table = tableRepository.findById(tableId).orElseThrow(() -> new IllegalArgumentException("Такого стола не существует"));
        if (encoder.matches(credentials.getPassword(), user.getPassword())) {
            val session = Session.builder()
                    .status(SessionStatus.OPENED)
                    .table(table)
                    .token(UUID.randomUUID().toString())
                    .user(user)
                    .build();
            session.willBeClosedAt();
            return Optional.of(DTOConverter.convertToDTO(session));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<SessionDTO> loginWithToken(String token) {
        val session = sessionRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Закончился срок жизни сессии"));
        return Optional.of(DTOConverter.convertToDTO(session));
    }

    @Override
    @Transactional
    public boolean logout(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        session.ifPresent(sessionRepository::delete);
        return true;
    }
}
