package ru.innopolis.stc27.maslakov.enterprise.project42.services;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final TableRepository tableRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.orElseThrow(
                () -> new UsernameNotFoundException(
                        MessageFormat.format("User with login {0} cannot be found.", login)
                )
        );
    }

    public Optional<Session> login(CredentialsDTO credentials, int tableNumber) {
        Optional<User> user = userRepository.findByLogin(credentials.getLogin());
        Optional<Table> table = tableRepository.findByNumber(tableNumber);
        if (user.isPresent() && table.isPresent()) {
            val hash = encoder.encode(credentials.getPassword());
            if (user.get().getPassword().equals(hash)) {
                val token = UUID.randomUUID().toString();
                val session = Session.builder()
                        .table(table.get())
                        .timeout(Timestamp.valueOf(LocalDateTime.now().plusHours(5L)))
                        .user(user.get())
                        .token(token)
                        .status(SessionStatus.OPENED)
                        .build();
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

}
