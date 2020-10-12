package ru.innopolis.stc27.maslakov.enterprise.project42.entities.table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Table {

    private Integer id;
    private int number;
    private TableStatus status;

}
