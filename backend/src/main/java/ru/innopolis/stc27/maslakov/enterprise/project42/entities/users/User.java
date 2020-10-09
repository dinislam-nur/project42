package ru.innopolis.stc27.maslakov.enterprise.project42.entities.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Data
@AllArgsConstructor
public class User {

    private Long id;
    private final String login;
    private String password;
    private int salt;
    private Role role;
}
