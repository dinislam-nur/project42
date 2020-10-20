package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.TableDTOConverter;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TableServiceImplTest {

    private static final UUID TABLE_ID = UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002");

    private TableRepository tableRepository;
    private SessionRepository sessionRepository;
    private TableService tableService;

    private Table table;
    private TableDTO answer;

    @BeforeEach
    void setUp() {
        tableRepository = Mockito.mock(TableRepository.class);
        sessionRepository = Mockito.mock(SessionRepository.class);
        tableService = new TableServiceImpl(tableRepository, sessionRepository);

        table = new Table(
                TABLE_ID,
                1,
                TableStatus.RESERVED
        );

        answer = TableDTOConverter.convert(table);
    }

    @Test
    void getTableTest() {
        Mockito.when(tableRepository.findById(TABLE_ID))
                .thenReturn(Optional.of(table));
        val result = tableService.getTable(table.getId());

        assertEquals(answer, result);
        assertThrows(IllegalStateException.class, () -> tableService.getTable(null));
    }

    @Test
    void openTableTest() {
        table.setStatus(TableStatus.NOT_RESERVED);
        Mockito.when(tableRepository.findById(TABLE_ID))
                .thenReturn(Optional.of(table));
        table.setStatus(TableStatus.RESERVED);
        Mockito.when(tableRepository.save(table))
                .thenReturn(table);

        val result = tableService.openTable(TABLE_ID);

        assertEquals(this.answer, result);
        assertThrows(IllegalStateException.class,
                () -> tableService.openTable(UUID.randomUUID() /*new TableDTO(UUID.randomUUID(), 1, TableStatus.NOT_RESERVED)*/));
    }

    @Test
    void closeTableTest() {
        val sessions = new ArrayList<Session>() {{
            add(new Session());
            add(new Session());
            add(new Session());
        }};
        Mockito.when(sessionRepository.findByTableId(TABLE_ID))
                .thenReturn(sessions);
        Mockito.when(tableRepository.findById(TABLE_ID))
                .thenReturn(Optional.of(table));
        table.setStatus(TableStatus.NOT_RESERVED);
        Mockito.when(tableRepository.save(table))
                .thenReturn(table);

        val result = tableService.closeTable(TABLE_ID);
        val answer = new TableDTO(TABLE_ID, 1, TableStatus.NOT_RESERVED);

        Mockito.verify(sessionRepository).findByTableId(TABLE_ID);
        Mockito.verify(sessionRepository, Mockito.times(sessions.size()))
                .delete(Mockito.any(Session.class));
        assertEquals(answer, result);
        assertThrows(IllegalStateException.class,
                () -> tableService.closeTable(UUID.randomUUID() /*new TableDTO(UUID.randomUUID(), 1, TableStatus.RESERVED)*/));
    }

    @Test
    void createTableTest() {

        table.setId(null);
        val returnedTable = new Table(TABLE_ID, 1, TableStatus.RESERVED);
        Mockito.when(tableRepository.save(table))
                .thenReturn(returnedTable);

        val inputTableDTO = new TableDTO(null, 1, TableStatus.RESERVED);
        val result = tableService.createTable(inputTableDTO);

        assertEquals(answer, result);
        assertThrows(IllegalStateException.class,
                () -> tableService.createTable(new TableDTO(TABLE_ID, 1, TableStatus.NOT_RESERVED)));

        val throwableTable = new Table(UUID.randomUUID(), 1, TableStatus.NOT_RESERVED);
        Mockito.when(tableRepository.save(throwableTable))
                .thenThrow(DataIntegrityViolationException.class);
        assertThrows(IllegalStateException.class,
                () -> tableService.createTable(TableDTOConverter.convert(throwableTable)));
    }

    @Test
    void deleteTableTest() {

        Mockito.when(tableRepository.existsById(TABLE_ID))
                .thenReturn(true);

        tableService.deleteTable(answer.getId());

        Mockito.verify(tableRepository).deleteById(TABLE_ID);

        Mockito.when(tableRepository.existsById(Mockito.any(UUID.class)))
                .thenReturn(false);
        assertThrows(IllegalStateException.class, () -> tableService.deleteTable(answer.getId()));
    }
}