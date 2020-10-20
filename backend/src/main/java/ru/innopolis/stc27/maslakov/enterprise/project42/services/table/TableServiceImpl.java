package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.ServicesUtils;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.TableDTOConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final SessionRepository sessionRepository;

    @Override
    public List<TableDTO> getTables(TableStatus status) {
        if (status != null) {
            return tableRepository
                    .findByStatus(status)
                    .stream()
                    .map(TableDTOConverter::convert)
                    .collect(Collectors.toList());
        } else {
            val tables = new ArrayList<TableDTO>();
            tableRepository
                    .findAll()
                    .forEach(
                            table -> tables.add(TableDTOConverter.convert(table))
                    );
            return tables;
        }
    }

    @Override
    @Transactional
    public TableDTO getTable(UUID tableId) {
        return TableDTOConverter.convert(
                tableRepository.findById(tableId)
                        .orElseThrow(() -> new IllegalStateException("Стола с id #" + tableId + " не существует"))
        );
    }

    @Override
    @Transactional
    public TableDTO changeStatus(UUID id, TableStatus status) {
        var table = tableRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("Стола с id #" + id + " не существует")
                );
        if (!table.getStatus().equals(status)) {
            table.setStatus(status);
            table = tableRepository.save(table);
        }
        if (status.equals(TableStatus.NOT_RESERVED)) {
            final List<Session> sessions = sessionRepository
                    .findByTableId(id);
            sessions.forEach(sessionRepository::delete);
        }
        return TableDTOConverter.convert(table);
    }

    @Override
    @Transactional
    public TableDTO createTable(TableDTO tableDTO) {
        try {
            ServicesUtils.checkTableDTO(tableDTO, true);
            val id = tableDTO.getId();
            if (id != null) {
                throw new IllegalStateException(
                        "Недопустимое состояние: сохранение записи стола с фиксированным id #" + id
                );
            }
            return TableDTOConverter.convert(
                    tableRepository.save(
                            TableDTOConverter.convertDTO(tableDTO)
                    )
            );
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException("Стол с номером #" + tableDTO.getNumber() + " уже существует", exception);
        }
    }

    @Override
    @Transactional
    public void deleteTable(UUID tableId) {
        if (tableRepository.existsById(tableId)) {
            tableRepository.deleteById(tableId);
        } else {
            throw new IllegalStateException("Удаление невозможно: стола с id #" + tableId + " не существует");
        }
    }
}
