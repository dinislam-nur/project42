package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;

import java.util.UUID;

public interface TableService {

    TableDTO getTable(UUID tableId);

}
