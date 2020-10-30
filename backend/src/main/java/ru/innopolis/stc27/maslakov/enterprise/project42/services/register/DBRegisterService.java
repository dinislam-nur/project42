package ru.innopolis.stc27.maslakov.enterprise.project42.services.register;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SignupGuestDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SignupStaffDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class DBRegisterService implements RegisterService {

    private final BCryptPasswordEncoder encoder;

    private final UserRepository repository;

    @Override
    public Optional<User> signup(SignupGuestDTO guest) {
        val encryptedPassword = encoder.encode(guest.getPassword());
        val user = User.builder()
                .login(guest.getLogin().toLowerCase())
                .password(encryptedPassword)
                .role(Role.ROLE_GUEST)
                .build();
        try {
            return Optional.of(repository.save(user));
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<User> signup(SignupStaffDTO stuff) {
        val encryptedPassword = encoder.encode(stuff.getPassword());
        val user = User.builder()
                .login(stuff.getLogin().toLowerCase())
                .password(encryptedPassword)
                .role(stuff.getRole())
                .build();
        try {
            return Optional.of(repository.save(user));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
}