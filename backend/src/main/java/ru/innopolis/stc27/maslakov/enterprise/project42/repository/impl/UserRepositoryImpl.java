package ru.innopolis.stc27.maslakov.enterprise.project42.repository.impl;

import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public List<User> findByRole(Role role) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User delete(User candidate) {
        return null;
    }
}
