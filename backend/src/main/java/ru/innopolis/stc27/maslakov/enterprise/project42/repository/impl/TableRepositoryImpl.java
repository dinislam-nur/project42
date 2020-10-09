package ru.innopolis.stc27.maslakov.enterprise.project42.repository.impl;

import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TableRepositoryImpl implements TableRepository {


    private final DataSource dataSource;

    @Autowired
    public TableRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Table> findAll() {
        final ArrayList<Table> result = new ArrayList<>();

        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tables as t " +
                            "JOIN table_statuses as ts " +
                            "ON t.table_status_id = ts.table_status_id");
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(createTable(resultSet));
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка соединения с базой данных");
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Table findById(long id) {
        Table result = null;
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tables as t " +
                            "JOIN table_statuses as ts " +
                            "ON t.table_status_id = ts.table_status_id " +
                            "WHERE t.table_id = ?");
            statement.setLong(1, id);
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = createTable(resultSet);
            } else {
                throw new IllegalStateException("Нет стола с таким id");
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка соединения с базой данных");
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Table> findByNumber(int number) {
        Table result = null;
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tables as t " +
                            "JOIN table_statuses as ts " +
                            "ON t.table_status_id = ts.table_status_id " +
                            "WHERE t.number = ?;"
            );
            statement.setInt(1, number);
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = createTable(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Table> findByStatus(TableStatus status) {
        List<Table> result = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tables as t " +
                            "JOIN table_statuses as ts " +
                            "ON t.table_status_id = ts.table_status_id " +
                            "WHERE ts.status_name = ?"
            );
            statement.setString(1, status.name());
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(createTable(resultSet));
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка соединения с базой данных");
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Table save(Table table) {
        Table result = null;
        try (final Connection connection = dataSource.getConnection()) {
            final Long tableId = table.getId();
            if (tableId == null) {
                @Cleanup final PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO tables (number, table_status_id) " +
                                "VALUES (?, " +
                                    "(SELECT table_status_id FROM table_statuses as ts " +
                                    "WHERE ts.status_name = ?));",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setInt(1, table.getNumber());
                statement.setString(2, table.getStatus().toString());
                statement.executeUpdate();
                @Cleanup final ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    final long generatedId = generatedKeys.getLong("table_id");
                    table.setId(generatedId);
                    result = table;
                }
            } else {
                try (final PreparedStatement statement = connection.prepareStatement(
                        "UPDATE tables as t " +
                                "SET number = ?, " +
                                "table_status_id = " +
                                    "(SELECT table_status_id FROM table_statuses as ts " +
                                    "WHERE ts.status_name = ?) " +
                                "WHERE t.table_id = ?")) {
                    statement.setInt(1, table.getNumber());
                    statement.setString(2, table.getStatus().toString());
                    statement.setLong(3, tableId);
                    statement.executeUpdate();
                    result = table;
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка соединения с базой данных");
            throwables.printStackTrace();
        }
        return result;
    }


    @Override
    public Table delete(Table candidate) {
        Table table = null;
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tables as t " +
                            "WHERE t.table_id = ?;"
                    );
            statement.setLong(1, candidate.getId());
            if (statement.executeUpdate() == 1) {
                table = candidate;
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка соединения с базой данных");
            throwables.printStackTrace();
        }
        return table;
    }

    private Table createTable(ResultSet resultSet) throws SQLException {
        final long tableId = resultSet.getLong("table_id");
        final int tableNumber = resultSet.getInt("number");
        final TableStatus tableStatus = TableStatus.valueOf(
                resultSet.getString("status_name"));
        return new Table(
                tableId,
                tableNumber,
                tableStatus);
    }
}
