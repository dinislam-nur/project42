package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
public class SignupGuestDTO {

    @Size(min = 5, message = "Логин должен содежать не менее 5 символов")
    @Pattern(regexp = "^[A-Za-z\\d]+[@#]?[A-Za-z\\d._]*$",
            message = "Логин может содержать только символы латинскового алфавита, цифры, '@', '#', '_' и '.'" +
                    "и должен начинаться с цифр и букв")
    String login;

    @Size(min = 5, max = 20, message = "Пароль должен содежать не менее 5 и не более 20 символов")
    String password;

}
