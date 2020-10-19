package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TableRepository extends PagingAndSortingRepository<Table, UUID> {

    Optional<Table> findByNumber(int number);

    List<Table> findByStatus(TableStatus status);

}
