package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CredentialsDTO {

    @NotNull(message = "Пустая строка 'Логин'")
    String login;

    @ToString.Exclude
    @NotNull(message = "Пустая строка 'Пароль'")
    String password;

}
