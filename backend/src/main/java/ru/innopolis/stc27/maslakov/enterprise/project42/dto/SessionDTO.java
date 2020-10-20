package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDTO {

    String token;

    UserDTO user;

    UUID tableId;

}
