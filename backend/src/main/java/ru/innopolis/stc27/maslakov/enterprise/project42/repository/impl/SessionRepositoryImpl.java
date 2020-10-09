package ru.innopolis.stc27.maslakov.enterprise.project42.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.SessionStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private static final String SESSION_ID = "id";
    private static final String SESSION_STATUS = "status";
    private static final String SESSION_TOKEN = "token";
    private static final String SESSION_TIMESTAMP = "timestamp";
    private static final String SESSION_TABLE = "table_id";
    private static final String SESSION_GUEST = "guest_id";


    private final DataSource dataSource;


    @Autowired
    public SessionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<List<Session>> findAll() {
        final String query = "SELECT * FROM sessions " +
                "LEFT JOIN guests " + "WHERE sessions.guest_id = guests.id " +
                "LEFT JOIN tables " + "WHERE sessions.table_id = table.id";

        List<Session> sessions = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement(
                    query/*, Statement.RETURN_GENERATED_KEYS*/)) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final int id = resultSet.getInt(SESSION_ID);
                    final SessionStatus status = SessionStatus.valueOf(resultSet.getString(SESSION_STATUS));
                    final String token = resultSet.getString(SESSION_TOKEN);
                    final long timestamp = resultSet.getLong(SESSION_TIMESTAMP);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Session findById(int id) {
        return null;
    }

    @Override
    public Optional<Session> findByGuest(User user) {
        return Optional.empty();
    }

    @Override
    public List<Session> findByTable(Table table) {
        return null;
    }

    @Override
    public List<Session> findAllOpenedSession() {
        return null;
    }

    @Override
    public Session save(Session session) {
        return null;
    }

    @Override
    public Session delete(Session session) {
        return null;
    }
}
