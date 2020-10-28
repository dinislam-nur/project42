package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CredentialsDTO {

    @NotNull(message = "Пустая строка 'Логин'")
    String login;

    @NotNull(message = "Пустая строка 'Пароль'")
    String password;

}
