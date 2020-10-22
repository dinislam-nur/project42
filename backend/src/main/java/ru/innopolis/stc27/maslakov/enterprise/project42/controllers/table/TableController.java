package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.table;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.table.TableService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping(path = "/tables")
    public List<TableDTO> tables(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "number", required = false) Integer number
    ) {
        try {
            return tableService.getTables(status, number);
        } catch (IllegalStateException exception) {
            throw exception;
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }

    @GetMapping(path = "/tables/{table_id}")
    public TableDTO table(@PathVariable(name = "table_id") UUID tableId) {
        return tableService.getTable(tableId);
    }

    @PostMapping(path = "/tables")
    public ResponseEntity<String> create(@RequestBody @NonNull TableDTO tableDTO) {
        try {
            val table = tableService.createTable(tableDTO);
            return ResponseEntity
                    .created(
                            URI.create("/tables/" + table.getId())
                    )
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Стол с номером #" + tableDTO.getNumber() + " уже существует", exception);
        }
    }

    @PutMapping(path = "/tables/{table_id}")
    public void changeStatus(
            @PathVariable("table_id") UUID id,
            @RequestBody @NonNull TableDTO tableDTO
    ) {
        tableService.updateTable(id, tableDTO);
    }

    @DeleteMapping(path = "/tables/{table_id}")
    public void delete(@PathVariable("table_id") UUID tableId) {
        tableService.deleteTable(tableId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalStateException.class)
    public String tableNotFoundStateHandler(IllegalStateException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String badRequestHandler() {
        val message = "Некорректный запрос";
        log.warn(message);
        return message;
    }
}
