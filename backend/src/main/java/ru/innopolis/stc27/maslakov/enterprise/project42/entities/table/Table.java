package ru.innopolis.stc27.maslakov.enterprise.project42.entities.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Table(name = "tables")
public class Table {

    @Id
    @Column(name = "table_id", nullable = false)
    @GeneratedValue(generator = "TABLE_ID_GENERATOR", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "TABLE_ID_GENERATOR", strategy = "uuid2")
    private UUID id;

    @Column(name = "number", nullable = false)
    private int number;

    @Convert(converter = TableStatusAttributeConverter.class)
    @Column(name = "table_status_id")
    private TableStatus status;

    @PrePersist
    public void defaultStatus() {
        status = TableStatus.NOT_RESERVED;
    }
}
