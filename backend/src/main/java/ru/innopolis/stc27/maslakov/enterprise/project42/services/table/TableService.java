package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.UUID;

public interface TableService {

    TableDTO getTable(UUID tableId);

    TableDTO openTable(TableDTO tableDTO);

    TableDTO closeTable(TableDTO tableDTO);

    TableDTO createTable(TableDTO tableDTO);

    void deleteTable(TableDTO tableDTO);

}
