package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.SessionRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public TableDTO getTable(UUID tableId) {
        log.info("Запрос на получение стола с id #" + tableId);
        return DTOConverter.convert(
                tableRepository.findById(tableId)
                        .orElseThrow(() -> new IllegalStateException("Стола с id #" + tableId + " не существует"))
        );
    }

    @Override
    @Transactional
    public List<TableDTO> getTables(String status, Integer number) {
        if (number != null) {
            log.info("Запрос на получение стола по номеру #" + number);
            val table = tableRepository
                    .findByNumber(number)
                    .orElseThrow(() -> new IllegalStateException("Стола с номером #" + number + " не существует"));
            return Collections.singletonList(DTOConverter.convert(table));
        } else if (status != null) {
            log.info("Запрос на получение столов по статусу" + status);
            return tableRepository
                    .findByStatus(TableStatus.valueOf(status))
                    .stream()
                    .map(DTOConverter::convert)
                    .collect(Collectors.toList());
        } else {
            log.info("Запрос на получение всех столов");
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
    public TableDTO createTable(TableDTO tableDTO) {
        log.info("Запрос на добавление стола: " + tableDTO);
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
    public void updateTable(UUID id, TableDTO tableDTO) {
        log.info("Запрос на обновление записи стола: " + tableDTO + " по id #" + id);
        if (id.equals(tableDTO.getId())) {
            tableRepository.save(DTOConverter.convertDTO(tableDTO));
            if (tableDTO.getStatus().equals(TableStatus.NOT_RESERVED)) {
                log.info("Удаление всех сессий по столу c id #" + id);
                final List<Session> sessions = sessionRepository
                        .findByTableId(id);
                sessions.forEach(sessionRepository::delete);
            }
        } else {
            throw new RuntimeException("Не совпадают id обращения и id стола");
        }
    }

    @Override
    @Transactional
    public void deleteTable(UUID tableId) {
        log.info("Удаление стола с id #" + tableId);
        if (tableRepository.existsById(tableId)) {
            tableRepository.deleteById(tableId);
        } else {
            throw new IllegalStateException("Удаление невозможно: стола с id #" + tableId + " не существует");
        }
    }
}
