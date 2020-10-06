package ru.innopolis.stc27.maslakov.enterprise.project42.entities.users;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class Guest {

    int id;

    String login;

    @Setter
    @NonFinal
    String password;
}
