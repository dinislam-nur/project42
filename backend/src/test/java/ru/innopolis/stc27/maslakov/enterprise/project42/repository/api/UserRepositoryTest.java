package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    private final UserRepository userRepository;
    private static Flyway flyway;

    private User answer;

    @Autowired
    UserRepositoryTest(UserRepository userRepository,
                       Flyway flywayBean) {
        this.userRepository = userRepository;
        flyway = flywayBean;
    }

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        answer = User.builder()
                .id(1L)
                .login("user")
                .password("$2y$10$MfJEpQhrvAo0M4lJXMfFCuTOtGyy8x79PpavQ7T.GnMPorKbTFzHy")
                .role(Role.ROLE_GUEST)
                .build();
    }

    @AfterAll
    static void afterAll() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void findAllTest() {
        final Iterable<User> users = userRepository.findAll();
        users.forEach(user -> log.info(user + " - поиск всех"));
        final User result = users.iterator().next();

        assertEquals(answer, result);
    }

    @Test
    void findByIdTest() {
        final User user = userRepository.findById(answer.getId()).orElse(null);
        log.info(user + " - поиск по id");

        assertEquals(answer, user);
    }

    @Test
    void findByLoginTest() {
        final User user = userRepository.findByLogin(answer.getLogin()).orElse(null);
        log.info(user + " - поиск по логину");

        assertEquals(answer, user);
    }

    @Test
    void insertTest() {
        final User admin = new User(null, "anton", "anton", Role.ROLE_ADMIN);
        final User saved = userRepository.save(admin);
        admin.setId(saved.getId());
        log.info(saved + " - запись сохранена");

        assertEquals(admin, saved);
    }

    @Test
    void updateTest() {
        answer.setRole(Role.ROLE_WAITER);
        final User updated = userRepository.save(answer);
        log.info(updated + " - запись обновлена");

        assertEquals(answer, updated);
    }

    @Test
    void deleteTest() {
        userRepository.delete(answer);
        log.info(answer + " - запись удалена");

        assertNull(userRepository.findById(answer.getId()).orElse(null));
    }
}