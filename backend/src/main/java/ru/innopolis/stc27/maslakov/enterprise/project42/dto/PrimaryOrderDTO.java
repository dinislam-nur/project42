package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Value
public class PrimaryOrderDTO {

    @NotNull(message = "Пустое поле 'Список блюд'")
    List<Long> foodsId;

    @NotNull(message = "Пустое поле 'Id стола'")
    UUID tableId;

}
