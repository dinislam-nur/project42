package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class TableDTO {

    UUID id;

    @NotNull(message = "Пустое поле 'Номер стола'")
    Integer number;

    TableStatus status;

}
