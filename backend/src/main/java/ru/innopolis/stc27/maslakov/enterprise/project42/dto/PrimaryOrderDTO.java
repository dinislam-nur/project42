package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class PrimaryOrderDTO {

    List<Long> foodsId;

    UUID tableId;

}
