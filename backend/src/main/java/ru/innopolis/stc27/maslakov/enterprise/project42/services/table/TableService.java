package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;

import java.util.List;
import java.util.UUID;

public interface TableService {

    TableDTO createTable(TableDTO tableDTO);

    TableDTO getTable(UUID id);

    List<TableDTO> getTables(String status, Integer number);

    void updateTable(UUID id, TableDTO tableDTO);

    void deleteTable(UUID tableId);

}
