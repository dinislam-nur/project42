package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;

@Value
public class SessionDTO {

    Long id;

    String token;

    UserDTO user;

    TableDTO table;

}
