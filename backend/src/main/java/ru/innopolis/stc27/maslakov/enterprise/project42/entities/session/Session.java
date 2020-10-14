package ru.innopolis.stc27.maslakov.enterprise.project42.entities.session;

import lombok.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Table(name = "sessions")
public class Session {

    @Id
    @Column(name = "session_id", nullable = false)
    @GeneratedValue(generator = "SESSION_ID_GENERATOR")
    @SequenceGenerator(name = "SESSION_ID_GENERATOR", allocationSize = 1, sequenceName = "sessions_session_id_seq")
    private Long id;

    @Column(name = "session_status_id", nullable = false)
    @Convert(converter = SessionStatusAttributeConverter.class)
    private SessionStatus status;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "timeout", nullable = false)
    private Timestamp timeout;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void willBeClosedAt() {
        final long day = TimeUnit.DAYS.toMillis(1);
        timeout = new Timestamp(System.currentTimeMillis() + day);
    }

}
