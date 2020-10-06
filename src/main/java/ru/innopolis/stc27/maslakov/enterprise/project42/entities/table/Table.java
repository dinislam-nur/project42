package ru.innopolis.stc27.maslakov.enterprise.project42.entities.table;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class Table {

    int id;

    @Setter
    @NonFinal
    TableStatus status;
}
