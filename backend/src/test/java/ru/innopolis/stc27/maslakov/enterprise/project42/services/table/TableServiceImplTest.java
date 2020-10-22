package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.util.ArrayList;
import java.util.Collections;
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

        answer = DTOConverter.convert(table);
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
    void getTablesTest(){
        Mockito.when(tableRepository.findByNumber(table.getNumber()))
                .thenReturn(Optional.of(table));
        val tablesDTOByNumber = tableService.getTables(null, 1);

        assertEquals(answer, tablesDTOByNumber.get(0));
        assertThrows(IllegalStateException.class, () -> tableService.getTables(null, 2));

        Mockito.when(tableRepository.findByStatus(table.getStatus()))
                .thenReturn(Collections.singletonList(table));
        val tablesDTOByStatus = tableService.getTables("RESERVED", null);

        assertEquals(answer, tablesDTOByStatus.get(0));

        Mockito.when(tableRepository.findAll())
                .thenReturn(Collections.singletonList(table));
        val tablesDTO = tableService.getTables(null, null);

        assertEquals(answer, tablesDTO.get(0));
    }

    @Test
    void updateTest() {
        val sessions = new ArrayList<Session>() {{
            add(new Session());
            add(new Session());
            add(new Session());
        }};
        Mockito.when(sessionRepository.findByTableId(TABLE_ID))
                .thenReturn(sessions);

        val inputTableDTO = new TableDTO(TABLE_ID, 1, TableStatus.NOT_RESERVED);
        tableService.updateTable(TABLE_ID, inputTableDTO);

        table.setStatus(TableStatus.NOT_RESERVED);
        Mockito.verify(tableRepository).save(table);
        Mockito.verify(sessionRepository).findByTableId(TABLE_ID);
        Mockito.verify(sessionRepository, Mockito.times(sessions.size()))
                .delete(Mockito.any(Session.class));
        assertThrows(
                RuntimeException.class,
                () -> tableService.updateTable(UUID.randomUUID(), inputTableDTO)
                );
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
        assertThrows(RuntimeException.class,
                () -> tableService.createTable(new TableDTO(TABLE_ID, 1, TableStatus.NOT_RESERVED)));
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