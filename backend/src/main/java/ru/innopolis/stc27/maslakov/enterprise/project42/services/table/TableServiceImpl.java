package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.ServicesUtils;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.TableDTOConverter;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final SessionRepository sessionRepository;

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
    public List<TableDTO> getTables(String status, Integer number) {
        if (number != null) {
            val table = tableRepository
                    .findByNumber(number)
                    .orElseThrow(() -> new IllegalStateException("Стола с номером #" + number + " не существует"));
            return Collections.singletonList(TableDTOConverter.convert(table));
        } else if (status != null) {
            return tableRepository
                    .findByStatus(TableStatus.valueOf(status))
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
    public TableDTO createTable(TableDTO tableDTO) {
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
    }

    @Override
    @Transactional
    public void updateTable(UUID id, TableDTO tableDTO) {
        if (id.equals(tableDTO.getId())) {
            tableRepository.save(TableDTOConverter.convertDTO(tableDTO));
            if (tableDTO.getStatus().equals(TableStatus.NOT_RESERVED)) {
                final List<Session> sessions = sessionRepository
                        .findByTableId(id);
                sessions.forEach(sessionRepository::delete);
            }
        } else {
            throw new RuntimeException("Неправильный запрос");
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
