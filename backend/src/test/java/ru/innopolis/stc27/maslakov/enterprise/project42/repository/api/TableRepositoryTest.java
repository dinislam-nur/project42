package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TableRepositoryTest {

    @Autowired
    private TableRepository tableRepository;

    @Test
    @Disabled
    void findAllTest() {
        final List<Table> all = tableRepository.findAll();
        final Table answer = new Table(2L, 1, TableStatus.NOT_RESERVED);
        for (Table table : all) {
            System.out.println(table);
        }
        assertEquals(answer, all.get(0));
    }

    @Test
    @Disabled
    void findByIdTest() {
        final Table answer = new Table(2L, 1, TableStatus.NOT_RESERVED);
        final Table result = tableRepository.findById(2L).orElse(null);
        System.out.println(result + " - по id");
        assertEquals(answer, result);
    }

    @Test
    @Disabled
    void findByNumberTest() {
        final Table answer = new Table(2L, 1, TableStatus.NOT_RESERVED);
        final Table result = tableRepository.findByNumber(answer.getNumber()).orElse(null);
        System.out.println(result + " - по номеру");
        assertEquals(answer, result);
    }

    @Test
    @Disabled
    void findByStatusTest() {
        final Table answer = new Table(2L, 1, TableStatus.NOT_RESERVED);
        final List<Table> tables = tableRepository.findByStatus(TableStatus.NOT_RESERVED);
        tables.forEach(table -> System.out.println(table + " - по статусу"));
        assertEquals(answer, tables.get(0));
    }

    @Test
    @Disabled
    void saveAndDeleteTest() {
        final Table table = new Table(null, 2, TableStatus.NOT_RESERVED);
        final Table answer = new Table(null, 2, TableStatus.NOT_RESERVED);
        final Table savedTable = tableRepository.save(table);
        answer.setId(savedTable.getId());
        System.out.println(savedTable + " - сохранена запись");

        assertEquals(answer, savedTable);

        answer.setStatus(TableStatus.RESERVED);
        savedTable.setStatus(TableStatus.RESERVED);
        final Table updatedTable = tableRepository.save(savedTable);
        System.out.println(updatedTable + " - запись обновлена");

        assertEquals(answer, updatedTable);

        tableRepository.delete(updatedTable);
    }
}