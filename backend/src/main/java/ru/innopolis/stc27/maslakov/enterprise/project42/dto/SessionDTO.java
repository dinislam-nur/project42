package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SessionDTO {

    String token;

    UserDTO user;

    Long tableId;

}
