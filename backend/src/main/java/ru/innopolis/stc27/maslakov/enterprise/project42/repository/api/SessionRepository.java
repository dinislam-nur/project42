package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends PagingAndSortingRepository<Session, Long> {

    Optional<Session> findByUser(User user);

    List<Session> findByTable(Table table);

    List<Session> findByStatus(SessionStatus sessionStatus);

    Optional<Session> findByToken(String token);
}
