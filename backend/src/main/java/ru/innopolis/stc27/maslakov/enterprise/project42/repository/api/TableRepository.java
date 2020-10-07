package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.List;

public interface TableRepository {

    List<Table> findAll();

    Table findById(int id);

    List<Table> findByStatus(TableStatus status);

    Table update(Table updatedTable);

    Table insert(Table newTable);

    Table delete(Table candidate);
}
