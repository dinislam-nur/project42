package ru.innopolis.stc27.maslakov.enterprise.project42.services.table;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.ServicesUtils;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.TableDTOConverter;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Override
    @Transactional
    public TableDTO getTable(UUID tableId) {
        return TableDTOConverter.convert(
                tableRepository.findById(tableId)
                        .orElseThrow(() -> new IllegalStateException("Стола с id #" + tableId + " не существует"))
        );
    }

    @Override
    public TableDTO openTable(TableDTO tableDTO) {
        ServicesUtils.checkTableDTO(tableDTO, false);
        if (tableDTO.getStatus() != TableStatus.RESERVED) {
            return changeStatus(tableDTO, TableStatus.RESERVED);
        }
        return tableDTO;
    }

    @Override
    public TableDTO closeTable(TableDTO tableDTO) {
        ServicesUtils.checkTableDTO(tableDTO, false);
        if (tableDTO.getStatus() != TableStatus.NOT_RESERVED) {
            return changeStatus(tableDTO, TableStatus.NOT_RESERVED);
        }
        return tableDTO;
    }

    @Transactional
    public TableDTO changeStatus(TableDTO tableDTO, TableStatus status) {
        val id = tableDTO.getId();
        if (tableRepository.existsById(id)) {
            val table = TableDTOConverter.convertDTO(tableDTO);
            table.setStatus(status);
            return TableDTOConverter.convert(
                    tableRepository.save(table)
            );
        } else {
            throw new IllegalStateException("Стола с id #" + id + " не существует");
        }

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
    public void deleteTable(TableDTO tableDTO) {
        ServicesUtils.checkTableDTO(tableDTO, false);
        val id = tableDTO.getId();
        if (tableRepository.existsById(id)) {
            tableRepository.delete(TableDTOConverter.convertDTO(tableDTO));
        } else {
            throw new IllegalStateException("Удаление невозможно: стола с id #" + id + " не существует");
        }
    }
}
