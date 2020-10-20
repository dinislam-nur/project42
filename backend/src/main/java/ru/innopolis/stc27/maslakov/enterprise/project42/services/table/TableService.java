package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.List;
import java.util.UUID;

public interface TableService {

    List<TableDTO> getTables(TableStatus status);

    TableDTO getTable(UUID id);

    TableDTO changeStatus(UUID id, TableStatus status);

    TableDTO createTable(TableDTO tableDTO);

    void deleteTable(UUID tableId);

}
