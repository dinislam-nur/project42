package ru.innopolis.stc27.maslakov.enterprise.project42.view.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
//    @NotNull
//    @NotEmpty
    private String firstName;

//    @NotNull
//    @NotEmpty
    private String lastName;

//    @NotNull
//    @NotEmpty
    private String password;
    private String matchingPassword;

//    @NotNull
//    @NotEmpty
    private String email;

    // standard getters and setters
}
