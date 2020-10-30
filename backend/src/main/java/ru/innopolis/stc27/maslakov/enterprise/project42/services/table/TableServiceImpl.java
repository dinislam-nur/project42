package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.session.SessionService;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    @Override
    @Transactional
    public TableDTO getTable(UUID tableId) {
        return DTOConverter.convert(
                tableRepository.findById(tableId)
                        .orElseThrow(() -> new IllegalStateException("Стола с id #" + tableId + " не существует"))
        );
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_WAITER', 'ROLE_CHIEF', 'ROLE_ADMIN')")
    public List<TableDTO> getTables(String status, Integer number) {
        if (number != null) {
            val table = tableRepository
                    .findByNumber(number)
                    .orElseThrow(() -> new IllegalStateException("Стола с номером #" + number + " не существует"));
            return Collections.singletonList(DTOConverter.convert(table));
        } else if (status != null) {
            return tableRepository
                    .findByStatus(TableStatus.valueOf(status))
                    .stream()
                    .map(DTOConverter::convert)
                    .collect(Collectors.toList());
        } else {
            val tables = new ArrayList<TableDTO>();
            tableRepository
                    .findAll()
                    .forEach(
                            table -> tables.add(DTOConverter.convert(table))
                    );
            return tables;
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TableDTO createTable(TableDTO tableDTO) {
        val id = tableDTO.getId();
        if (id != null) {
            throw new RuntimeException(
                    "Недопустимое состояние: сохранение записи стола с фиксированным id #" + id
            );
        }
        return DTOConverter.convert(
                tableRepository.save(
                        DTOConverter.convertDTO(tableDTO)
                )
        );
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_WAITER', 'ROLE_ADMIN')")
    public void updateTable(UUID id, TableDTO tableDTO) {
        if (id.equals(tableDTO.getId())) {
            tableRepository.save(DTOConverter.convertDTO(tableDTO));
            if (tableDTO.getStatus().equals(TableStatus.NOT_RESERVED)) {
                sessionRepository
                        .findByTableId(id)
                        .forEach(sessionService::deleteAnonymousOrSession);
            }
        } else {
            throw new RuntimeException("Не совпадают id обращения и id стола");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteTable(UUID tableId) {
        if (tableRepository.existsById(tableId)) {
            tableRepository.deleteById(tableId);
        } else {
            throw new IllegalStateException("Удаление невозможно: стола с id #" + tableId + " не существует");
        }
    }
}
