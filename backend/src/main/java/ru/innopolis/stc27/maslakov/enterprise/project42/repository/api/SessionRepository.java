package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    Optional<List<Session>> findAll();

    Session findById(int id);

    Optional<Session> findByGuest(User user);

    List<Session> findByTable(Table table);

    List<Session> findAllOpenedSession();

    Session save(Session session);

    Session delete(Session session);
}
