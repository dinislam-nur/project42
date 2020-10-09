package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findByRole(Role role);

    User save(User newUser);

    Optional<User> delete(User candidate);
}
