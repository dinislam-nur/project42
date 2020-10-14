package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

@Value
public class TableDTO {

    Long id;

    Integer table;

    TableStatus status;

}
