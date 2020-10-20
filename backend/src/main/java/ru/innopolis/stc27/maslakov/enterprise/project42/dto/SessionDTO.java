package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SessionDTO {

    String token;

    UserDTO user;

    UUID tableId;

}
