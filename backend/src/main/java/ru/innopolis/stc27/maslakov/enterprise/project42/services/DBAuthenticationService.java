package ru.innopolis.stc27.maslakov.enterprise.project42.services;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SessionDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.UserDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.OrderRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.api.AuthenticationService;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DBAuthenticationService implements AuthenticationService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.orElseThrow(
                () -> new UsernameNotFoundException(
                        MessageFormat.format("User with login {0} cannot be found.", login)
                )
        );
    }

    @Override
    public Optional<SessionDTO> loginWithCredentials(CredentialsDTO credentials, Long tableId) {
        Optional<User> user = userRepository.findByLogin(credentials.getLogin());
        Optional<Table> table = tableRepository.findById(tableId);
        if (user.isPresent() && table.isPresent()) {
            if (encoder.matches(credentials.getPassword(), user.get().getPassword())) {
                Session session = createSession(user.get(), table.get());
                val userDTO = UserDTO.builder()
                        .id(user.get().getId())
                        .login(user.get().getLogin())
                        .role(user.get().getRole())
                        .build();
                val sessionDTO = SessionDTO.builder()
                        .tableId(table.get().getId())
                        .user(userDTO)
                        .token(session.getToken())
                        .build();
                return Optional.of(sessionDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<SessionDTO> loginWithToken(String token) {
        val session = sessionRepository.findByToken(token);
        return session.map(value -> SessionDTO.builder()
                .token(value.getToken())
                .tableId(value.getTable().getId())
                .user(UserDTO.builder()
                        .id(value.getUser().getId())
                        .login(value.getUser().getLogin())
                        .role(value.getUser().getRole())
                        .build()
                ).build());
    }

    @Override
    public boolean logout(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        session.ifPresent(sessionRepository::delete);
        return true;
    }

    private Session createSession(User user, Table table) {
        val token = UUID.randomUUID().toString();

        val session = Session.builder()
                .status(SessionStatus.OPENED)
                .token(token)
                .table(table)
                .user(user)
                .build();
        session.willBeClosedAt();

        return sessionRepository.save(session);
    }

}
