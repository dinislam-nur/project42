package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Builder;
import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
public class SignupDTO {

    @Size(min = 5, message = "Login должен содежать не менее 5 символов")
    @Pattern(regexp = "^[A-Za-z\\d]+[@#]?[A-Za-z\\d._]*$",
            message = "Login может содержать только символы латинскового алфавита, цифры, '@', '#', '_' и '.'" +
                    "и должен начинаться с цифр и букв")
    String login;

    @Size(min = 5, max = 20, message = "Password должен содежать не менее 5 и не более 20 символов")
    String password;

    @Builder.Default
    Role role = Role.ROLE_GUEST;
}
