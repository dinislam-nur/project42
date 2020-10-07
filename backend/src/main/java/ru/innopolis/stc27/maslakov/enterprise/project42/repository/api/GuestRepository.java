package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Guest;

import java.util.Optional;

public interface GuestRepository {

    Guest findById(int id);

    Optional<Guest> findByLogin(String login);

    Guest updatePassword(String login, String oldPassword, String newPassword, String passwordConfirmation);

    Guest insert(Guest newGuest);

    Guest delete(Guest candidate);
}
