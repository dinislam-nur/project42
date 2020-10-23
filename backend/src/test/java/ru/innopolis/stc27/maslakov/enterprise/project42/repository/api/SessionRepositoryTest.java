package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Disabled
class SessionRepositoryTest {

    private final SessionRepository sessionRepository;
    private final Flyway flyway;

    private Session answer;

    @Autowired
    public SessionRepositoryTest(SessionRepository sessionRepository,
                                 Flyway flyway) {
        this.sessionRepository = sessionRepository;
        this.flyway = flyway;
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
                        .password("user")
                        .role(Role.ROLE_GUEST)
                        .build())
                .token("some_token")
                .table(Table.builder()
                        .id(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"))
                        .number(1)
                        .status(TableStatus.NOT_RESERVED)
                        .build())
                .status(SessionStatus.OPENED)
                .timeout(Timestamp.valueOf("2020-10-15 00:00:00.000000"))
                .build();
    }

    @Test
    void findAllTest() {
        final Iterable<Session> sessions = sessionRepository.findAll();
        sessions.forEach(session -> System.out.println(session + " - поиск всех"));

        final Session result = sessions.iterator().next();

        assertEquals(answer, result);
    }

    @Test
    void findByIdTest() {
        final Session result = sessionRepository.findById(1L).orElse(null);
        System.out.println(result + " - поиск по id");

        assertEquals(answer, result);
    }

    @Test
    void findByToken() {
        final Session result = sessionRepository.findByToken("some_token").orElse(null);
        System.out.println(result + " - поиск по token");

        assertEquals(answer, result);
    }

    @Test
    void findByUser() {
        final Session result = sessionRepository.findByUser(answer.getUser()).orElse(null);
        System.out.println(result + " - поиск по пользователю");

        assertEquals(answer, result);
    }

    @Test
    void findByStatus() {
        final List<Session> result = sessionRepository.findByStatus(SessionStatus.OPENED);
        result.forEach(session -> System.out.println(session + " - поиск по статусу сессии"));

        assertEquals(answer, result.get(0));
    }

    @Test
    void findByTableIdTest() {
        final List<Session> sessions = sessionRepository.findByTableId(answer.getTable().getId());
        sessions.forEach(session -> System.out.println(session + " - поиск по столу"));

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
                .status(SessionStatus.OPENED)
                .build();
        final Session saved = sessionRepository.save(newSession);
        System.out.println(saved + " - запись сохранена");
        newSession.setId(saved.getId());

        assertEquals(newSession, saved);
    }

    @Test
    void updateTest() {
        answer.setStatus(SessionStatus.CLOSED);
        final Session updated = sessionRepository.save(answer);
        System.out.println(updated + " - запись обновлена");

        assertEquals(answer, updated);
    }

    @Test
    void deleteTest() {
        sessionRepository.delete(answer);
        System.out.println(answer + " - запись удалена");
        assertNull(sessionRepository.findById(answer.getId()).orElse(null));
    }
}