package ru.innopolis.stc27.maslakov.enterprise.project42.utils;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;

import java.util.UUID;

public class TableDTOConverter {

    public static TableDTO convert(Table table) {
        return new TableDTO(
                table.getId(),
                table.getNumber(),
                table.getStatus()
        );
    }

    public static Table convertDTO(TableDTO tableDTO) {
        return new Table(
                tableDTO.getId(),
                tableDTO.getNumber(),
                tableDTO.getStatus()
        );
    }
}
