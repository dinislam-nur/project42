package ru.innopolis.stc27.maslakov.enterprise.project42.utils;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;

public class TableDTOConverter {

    public static TableDTO convert(Table table) {
        return new TableDTO(
                table.getId(),
                table.getNumber(),
                table.getStatus()
        );
    }
}
