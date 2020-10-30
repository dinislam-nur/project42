package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
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

@Slf4j
@SpringBootTest
class TableRepositoryTest {

    private final TableRepository tableRepository;
    private static Flyway flyway;

    private Table answer;

    @Autowired
    TableRepositoryTest(TableRepository tableRepository,
                        Flyway flywayBean) {
        this.tableRepository = tableRepository;
        flyway = flywayBean;
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

    @AfterAll
    static void afterAll() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void findAllTest() {
        final Iterable<Table> tables = tableRepository.findAll();
        tables.forEach(table -> log.info(table + " - поиск всех"));

        final Table result = tables.iterator().next();
        assertEquals(answer, result);
    }

    @Test
    void findByIdTest() {
        final Table result = tableRepository.findById(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002")).orElse(null);
        log.info(result + " - поиск по id");

        assertEquals(answer, result);
    }

    @Test
    void findByNumberTest() {
        final Table result = tableRepository.findByNumber(answer.getNumber()).orElse(null);
        log.info(result + " - поиск по номеру");

        assertEquals(answer, result);
    }

    @Test
    void findByStatusTest() {
        final List<Table> tables = tableRepository.findByStatus(TableStatus.NOT_RESERVED);
        tables.forEach(table -> log.info(table + " - поиск по статусу"));

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
        log.info(saved + " - запись сохранена");

        assertEquals(newTable, saved);
    }

    @Test
    void updateTest() {
        answer.setStatus(TableStatus.RESERVED);
        final Table updated = tableRepository.save(answer);
        log.info(updated + " - запись обновлена");

        assertEquals(answer, updated);
    }

    @Test
    void deleteTest() {
        answer.setId(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120003"));
        tableRepository.delete(answer);
        log.info(answer + " - запись удалена");

        assertNull(tableRepository.findById(answer.getId()).orElse(null));
    }
}