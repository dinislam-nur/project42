package ru.innopolis.stc27.maslakov.enterprise.project42.entities.session;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Guest;

@Value
public class Session {

    int id;

    @NonFinal
    @Setter
    SessionStatus status;

    String token;
    long timestamp;

    Table table;
    Guest guest;
}
