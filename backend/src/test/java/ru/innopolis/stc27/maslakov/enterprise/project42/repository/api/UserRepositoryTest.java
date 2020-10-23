package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final SessionRepository sessionRepository;
    private final Flyway flyway;

    private User answer;

    @Autowired
    UserRepositoryTest(UserRepository userRepository,
                       OrderRepository orderRepository,
                       SessionRepository sessionRepository,
                       Flyway flyway) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.sessionRepository = sessionRepository;
        this.flyway = flyway;
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

    @Test
    void findAllTest() {
        final Iterable<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user + " - поиск всех"));
        final User result = users.iterator().next();

        assertEquals(answer, result);
    }

    @Test
    void findByIdTest() {
        final User user = userRepository.findById(answer.getId()).orElse(null);
        System.out.println(user + " - поиск по id");

        assertEquals(answer, user);
    }

    @Test
    void findByLoginTest() {
        final User user = userRepository.findByLogin(answer.getLogin()).orElse(null);
        System.out.println(user + " - поиск по логину");

        assertEquals(answer, user);
    }

    @Test
    void findByRoleTest() {
        final List<User> users = userRepository.findByRole(Role.ROLE_GUEST);
        users.forEach(user -> System.out.println(user + " - поиск по роли"));

        assertEquals(answer, users.get(0));
    }

    @Test
    void insertTest() {
        final User admin = new User(null, "admin", "admin", Role.ROLE_ADMIN);
        final User saved = userRepository.save(admin);
        admin.setId(saved.getId());
        System.out.println(saved + " - запись сохранена");

        assertEquals(admin, saved);
    }

    @Test
    void updateTest() {
        answer.setRole(Role.ROLE_WAITER);
        final User updated = userRepository.save(answer);
        System.out.println(updated + " - запись обновлена");

        assertEquals(answer, updated);
    }

    @Test
    void deleteTest() {
        userRepository.delete(answer);
        System.out.println(answer + " - запись удалена");

        assertNull(userRepository.findById(answer.getId()).orElse(null));
    }
}