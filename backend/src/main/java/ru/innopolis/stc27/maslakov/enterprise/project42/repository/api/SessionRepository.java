package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends PagingAndSortingRepository<Session, Long> {

    Optional<Session> findByUser(User user);

    @Query(value = "SELECT s FROM Session s WHERE s.table.id = :table_id")
    List<Session> findByTableId(@Param("table_id") UUID tableId);

    List<Session> findByStatus(SessionStatus sessionStatus);

    Optional<Session> findByToken(String token);
}
