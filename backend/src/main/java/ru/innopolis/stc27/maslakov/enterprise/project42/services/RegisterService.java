package ru.innopolis.stc27.maslakov.enterprise.project42.services;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SignupDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;

import java.text.MessageFormat;
import java.util.Optional;


@Service
@AllArgsConstructor
public class RegisterService {

    private final BCryptPasswordEncoder encoder;

    private final UserRepository repository;

    public Optional<User> signup(SignupDTO data) {
        val encryptedPassword = encoder.encode(data.getPassword());
        val user = User.builder()
                .login(data.getLogin())
                .password(encryptedPassword)
                .role(data.getRole() == null ? Role.ROLE_GUEST : data.getRole())
                .build();
        try {
            return Optional.of(repository.save(user));
        }catch (Exception e) {
            return Optional.empty();
        }
    }
}
