package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Disabled
class TableRepositoryTest {

    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;
    private final SessionRepository sessionRepository;
    private final Flyway flyway;

    private Table answer;

    @Autowired
    TableRepositoryTest(TableRepository tableRepository,
                        OrderRepository orderRepository,
                        SessionRepository sessionRepository,
                        Flyway flyway) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.sessionRepository = sessionRepository;
        this.flyway = flyway;
    }

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        answer = Table.builder()
                .id(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"))
                .number(1)
                .status(TableStatus.NOT_RESERVED)
                .build();
    }

    @Test
    void findAllTest() {
        final Iterable<Table> tables = tableRepository.findAll();
        tables.forEach(table -> System.out.println(table + " - поиск всех"));

        final Table result = tables.iterator().next();
        assertEquals(answer, result);
    }

    @Test
    void findByIdTest() {
        final Table result = tableRepository.findById(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002")).orElse(null);
        System.out.println(result + " - поиск по id");

        assertEquals(answer, result);
    }

    @Test
    void findByNumberTest() {
        final Table result = tableRepository.findByNumber(answer.getNumber()).orElse(null);
        System.out.println(result + " - поиск по номеру");

        assertEquals(answer, result);
    }

    @Test
    void findByStatusTest() {
        final List<Table> tables = tableRepository.findByStatus(TableStatus.NOT_RESERVED);
        tables.forEach(table -> System.out.println(table + " - поиск по статусу"));

        assertEquals(answer, tables.get(0));
    }

    @Test
    void insertTest() {
        final Table newTable = Table.builder()
                .number(42)
                .status(TableStatus.NOT_RESERVED)
                .build();
        final Table saved = tableRepository.save(newTable);
        newTable.setId(saved.getId());
        System.out.println(saved + " - запись сохранена");

        assertEquals(newTable, saved);
    }

    @Test
    void updateTest() {
        answer.setStatus(TableStatus.RESERVED);
        final Table updated = tableRepository.save(answer);
        System.out.println(updated + " - запись обновлена");

        assertEquals(answer, updated);
    }

    @Test
    void deleteTest() {
        answer.setId(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120003"));
        tableRepository.delete(answer);
        System.out.println(answer + " - запись удалена");

        assertNull(tableRepository.findById(answer.getId()).orElse(null));
    }
}