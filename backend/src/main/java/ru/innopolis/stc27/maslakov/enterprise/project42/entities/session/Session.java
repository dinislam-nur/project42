package ru.innopolis.stc27.maslakov.enterprise.project42.entities.session;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

@Builder
@ToString
@EqualsAndHashCode
public class Session {

    private Integer id;
    private SessionStatus status;
    private final String token;
    private long timestamp;
    private Table table;
    private final User user;

}
