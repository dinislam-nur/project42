package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends CrudRepository<Table, Long> {

    List<Table> findAll();

    Optional<Table> findById(Long id);

    Optional<Table> findByNumber(int number);

    List<Table> findByStatus(TableStatus status);

    Table save(Table newTable);

    void delete(Table candidate);
}
