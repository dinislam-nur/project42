package ru.innopolis.stc27.maslakov.enterprise.project42.repository.impl;

import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users as u " +
                            "JOIN user_roles as ur " +
                            "ON u.user_role_id = ur.user_role_id;"
            );
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка подключения к базе данных");
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(long id) {
        User user = null;
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users as u " +
                            "JOIN user_roles as ur " +
                            "ON u.user_role_id = ur.user_role_id " +
                            "WHERE u.user_id = ?;"
            );
            statement.setLong(1, id);
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            } else {
                throw new IllegalStateException("Нет user с таким id");
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка подключения к базе данных");
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        User user = null;
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users as u " +
                            "JOIN user_roles as ur " +
                            "ON u.user_role_id = ur.user_role_id " +
                            "WHERE u.login = ?;"
            );
            statement.setString(1, login);
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = createUser(resultSet);
        } catch (SQLException throwables) {
            System.out.println("Ошибка подключения к базе данных");
            throwables.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findByRole(Role role) {
        List<User> users = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users as u " +
                            "JOIN user_roles as ur " +
                            "ON u.user_role_id = ur.user_role_id " +
                            "WHERE ur.role_name = ?;"
            );
            statement.setString(1, role.name());
            @Cleanup final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка подключения к базе данных");
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) {
        User result = null;
        try (final Connection connection = dataSource.getConnection()) {
            if (user.getId() == null) {
                @Cleanup final PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO users (login, password, salt, user_role_id)" +
                                "VALUES (?, ?, ?, " +
                                "(SELECT user_role_id FROM user_roles as ur " +
                                "WHERE ur.role_name = ?));",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setInt(3, user.getSalt());
                statement.setString(4, user.getRole().name());
                if (statement.executeUpdate() == 1) {
                    @Cleanup final ResultSet generatedId = statement.getGeneratedKeys();
                    if (generatedId.next()) {
                        final long userId = generatedId.getLong("user_id");
                        user.setId(userId);
                        result = user;
                    }
                }
            } else {
                @Cleanup final PreparedStatement statement = connection.prepareStatement(
                        "UPDATE users as u " +
                                "SET login = ?," +
                                "password = ?," +
                                "salt = ?," +
                                "user_role_id = " +
                                "(SELECT user_role_id FROM user_roles as ur " +
                                "WHERE ur.role_name = ?)" +
                                "WHERE u.user_id = ?"
                );
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setInt(3, user.getSalt());
                statement.setString(4, user.getRole().name());
                statement.setLong(5, user.getId());
                if (statement.executeUpdate() == 1) {
                    result = user;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<User> delete(User candidate) {
        User user = null;
        try (final Connection connection = dataSource.getConnection()) {
            @Cleanup final PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users as u " +
                            "WHERE u.user_id = ?;"
            );
            statement.setLong(1, candidate.getId());
            if (statement.executeUpdate() == 1) {
                user = candidate;
            }
        } catch (SQLException throwables) {
            System.out.println("Ошибка соединения с базой данных");
            throwables.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        final long userId = resultSet.getLong("user_id");
        final String login = resultSet.getString("login");
        final String password = resultSet.getString("password");
        final int salt = resultSet.getInt("salt");
        final Role role = Role.valueOf(resultSet.getString("role_name"));
        return new User(
                userId,
                login,
                password,
                salt,
                role
        );
    }
}
