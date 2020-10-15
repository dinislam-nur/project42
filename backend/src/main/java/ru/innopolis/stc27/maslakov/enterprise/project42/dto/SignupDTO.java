package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Builder;
import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;

@Value
@Builder
public class SignupDTO {

    String login;

    String password;

    @Builder.Default
    Role role = Role.ROLE_GUEST;
}
