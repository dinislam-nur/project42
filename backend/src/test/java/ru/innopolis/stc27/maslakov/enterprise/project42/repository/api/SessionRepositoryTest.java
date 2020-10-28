package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@Disabled
@SpringBootTest
class SessionRepositoryTest {

    private final SessionRepository sessionRepository;
    private static Flyway flyway = null;

    private Session answer;

    @Autowired
    public SessionRepositoryTest(SessionRepository sessionRepository,
                                 Flyway flywayBean) {
        this.sessionRepository = sessionRepository;
        flyway = flywayBean;
    }

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        answer = Session.builder()
                .id(1L)
                .user(User.builder()
                        .id(1L)
                        .login("user")
                        .password("$2y$10$MfJEpQhrvAo0M4lJXMfFCuTOtGyy8x79PpavQ7T.GnMPorKbTFzHy")
                        .role(Role.ROLE_GUEST)
                        .build())
                .token("some_token")
                .table(Table.builder()
                        .id(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"))
                        .number(1)
                        .status(TableStatus.NOT_RESERVED)
                        .build())
                .timeout(Timestamp.valueOf("2020-10-15 00:00:00.000000"))
                .build();
    }

    @AfterAll
    static void afterAll() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void findAllTest() {
        final Iterable<Session> sessions = sessionRepository.findAll();
        sessions.forEach(session -> log.info(session + " - поиск всех"));

        final Session result = sessions.iterator().next();

        assertEquals(answer, result);
    }

    @Test
    void findByIdTest() {
        final Session result = sessionRepository.findById(1L).orElse(null);
        log.info(result + " - поиск по id");

        assertEquals(answer, result);
    }

    @Test
    void findByTokenTest() {
        final Session result = sessionRepository.findByToken("some_token").orElse(null);
        log.info(result + " - поиск по token");

        assertEquals(answer, result);
    }

    @Test
    void findByUserTest() {
        final Session result = sessionRepository.findByUser(answer.getUser()).orElse(null);
        log.info(result + " - поиск по пользователю");

        assertEquals(answer, result);
    }

    @Test
    void findByTableIdTest() {
        final List<Session> sessions = sessionRepository.findByTableId(answer.getTable().getId());
        sessions.forEach(session -> log.info(session + " - поиск по столу"));

        assertEquals(answer, sessions.get(0));
    }

    @Test
    void insertTest() {
        final Session newSession = Session.builder()
                .id(null)
                .user(User.builder()
                        .id(1L)
                        .login("user")
                        .password("user")
                        .role(Role.ROLE_GUEST)
                        .build())
                .token("other_token")
                .table(Table.builder()
                        .id(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"))
                        .number(1)
                        .status(TableStatus.NOT_RESERVED)
                        .build())
                .build();
        final Session saved = sessionRepository.save(newSession);
        log.info(saved + " - запись сохранена");
        newSession.setId(saved.getId());

        assertEquals(newSession, saved);
    }

    @Test
    void deleteTest() {
        sessionRepository.delete(answer);
        log.info(answer + " - запись удалена");
        assertNull(sessionRepository.findById(answer.getId()).orElse(null));
    }
}