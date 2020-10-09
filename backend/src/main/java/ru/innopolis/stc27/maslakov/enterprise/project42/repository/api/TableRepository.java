package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.List;
import java.util.Optional;

public interface TableRepository {

    List<Table> findAll();

    Table findById(long id);

    Optional<Table> findByNumber(int number);

    List<Table> findByStatus(TableStatus status);

    Table save(Table newTable);

    Table delete(Table candidate);
}
