package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Disabled
    void findAllTest() {
        final User answer = new User(1L, "admin", "admin", 1, Role.GUEST);
        final List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user + " - поиск всех"));

        assertEquals(answer, users.get(0));
    }

    @Test
    @Disabled
    void findById() {
        final User answer = new User(1L, "admin", "admin", 1, Role.GUEST);
        final User user = userRepository.findById(answer.getId()).orElse(null);
        System.out.println(user + " - поиск по id");

        assertEquals(answer, user);
    }

    @Test
    @Disabled
    void findByLogin() {
        final User answer = new User(1L, "admin", "admin", 1, Role.GUEST);
        final User user = userRepository.findByLogin(answer.getLogin()).orElse(null);
        System.out.println(user + " - поиск по логину");

        assertEquals(answer, user);
    }

    @Test
    @Disabled
    void findByRole() {
        final User answer = new User(1L, "admin", "admin", 1, Role.GUEST);
        final List<User> users = userRepository.findByRole(Role.GUEST);
        users.forEach(user -> System.out.println(user + " - поиск по роли"));

        assertEquals(answer, users.get(0));

        assertEquals(0, userRepository.findByRole(Role.STAFF).size());
    }

    @Test
    @Disabled
    void save() {
        final User answer = new User(null, "user", "user", 2, Role.GUEST);
        final User savedUser = userRepository.save(answer);
        answer.setId(savedUser.getId());
        System.out.println(savedUser + " - добавлен пользователь");

        assertEquals(answer, savedUser);

        savedUser.setRole(Role.STAFF);
        final User updatedUser = userRepository.save(savedUser);
        answer.setRole(Role.STAFF);
        System.out.println(savedUser + " - пользователь обновлен");

        assertEquals(answer, updatedUser);

        userRepository.delete(updatedUser);
    }
}